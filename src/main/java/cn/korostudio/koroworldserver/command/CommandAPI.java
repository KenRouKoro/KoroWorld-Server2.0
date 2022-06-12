package cn.korostudio.koroworldserver.command;

import cn.hutool.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;

public class CommandAPI {

    protected static CommandNode root = CommandNode.creat("root",e->{});

    public static void register(CommandNode commandNode){
        root.then(commandNode);
    }
    protected static void run(JSONObject dataPack, WebSocketSession session){
        String commandStr = dataPack.getStr("command");
        String []commands = (commandStr.trim()).split(" ");
        commands[0] = commands[0].substring(1);
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
            return;
        }
        source.setData(dataPack.getStr("data"));
        commandNode.run(source);
    }

}
