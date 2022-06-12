package cn.korostudio.koroworldserver.command;

import cn.hutool.json.JSONObject;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

public abstract class CommandNode implements Node{
    @Getter
    protected String nodeName;
    @Getter
    protected ConcurrentHashMap<String, CommandNode> children = new ConcurrentHashMap<>();

    static public CommandNode creat(String name,Node commandNode){
        return new CommandNode() {
            {
            nodeName = name;
            }
            @Override
            public void run(CommandSource source) {
                commandNode.run(source);
            }
        };
    }

    public void then(CommandNode commandNode){
        children.put(commandNode.nodeName, commandNode);
    }

    public void remove(CommandNode commandNode){
        if(children.contains(commandNode)){
            children.remove(commandNode.nodeName);
        }

    }
}
