package cn.korostudio.koroworldserver.command;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;
@Slf4j
public class CommandAPI {

    protected static CommandNode root = CommandNode.creat("root",e->{});

    public static void register(CommandNode commandNode){
        root.then(commandNode);
    }
    public static void run(JSONObject dataPack, WebSocketSession session){
        String commandStr = dataPack.getStr("command");
        String []commands = commandStr.split(" ");
        CommandNode commandNode = root;
        CommandSource source = new CommandSource();
        source.setSource(dataPack);
        source.setSession(session);
        for(String command:commands){
            if(commandNode.children.containsKey(command)) {
                source.setNode(command);
                commandNode = commandNode.children.get(command);
            }else{
                break;
            }
        }
        if(commandNode==root){
            log.error("Why is Root?????");
            return;
        }
        source.setData(dataPack.getStr("data"));
        commandNode.run(source);
    }

}
