package cn.korostudio.koroworldserver.command;

import lombok.Getter;

import java.util.Map;
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
            public synchronized void run(CommandSource source) {
                commandNode.run(source);
            }
        };
    }

    public CommandNode then(CommandNode commandNode){
        if(!children.containsKey(commandNode.nodeName)) {
            children.put(commandNode.nodeName, commandNode);
        }else{
            Map<String,CommandNode> map = children.get(commandNode.nodeName).children;
            for(CommandNode commandNode1:commandNode.children.values()){
                if(!map.containsKey(commandNode1.nodeName)){
                    map.put(commandNode1.nodeName,commandNode1);
                }else{
                    map.get(commandNode1.nodeName).then(commandNode1);
                }
            }
        }
        return this;
    }

    public void remove(CommandNode commandNode){
        if(children.contains(commandNode)){
            children.remove(commandNode.nodeName);
        }

    }
}
