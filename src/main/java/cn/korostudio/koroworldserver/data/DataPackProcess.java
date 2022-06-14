package cn.korostudio.koroworldserver.data;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.korostudio.koroworldserver.command.CommandAPI;
import cn.korostudio.koroworldserver.wsservice.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DataPackProcess {

    protected static ConcurrentHashMap<String,DataPackProcessor> Processes = new ConcurrentHashMap<>();

    public static void run(String dataPack, WebSocketSession session){
        run(JSONUtil.parseObj(dataPack),session);
    }

    public static void register(String name,DataPackProcessor processor){
        Processes.put(name,processor);
    }

    static {
        DataPackProcessor commandDataPackProcess = (dataPack, session) -> {
            CommandAPI.run(dataPack,session);
            return null;
        };


        register("command",commandDataPackProcess);
    }

    public static void run(JSONObject dataPack,WebSocketSession session)  {
        log.debug(dataPack.toString());
        String type = dataPack.getStr("type");
        if (type==null)return;
        if(Processes.containsKey(type)){
            Processes.get(type).run(dataPack,session);
        }else{
            String target = dataPack.getStr("target");
            if(target.equals("ALL")){
                for(String name: WebSocketService.getServers().keySet()){
                    try {
                        WebSocketService.getServers().get(name).sendMessage(new TextMessage(dataPack.toString()));
                    } catch (IOException ignored) {
                        log.error("Send Error in send message to "+name);
                    }
                }
            }else if(!target.equals("SERVERONLY")){
                if(WebSocketService.getServers().containsKey(target))return;
                try {
                    WebSocketService.getServers().get(target).sendMessage(new TextMessage(dataPack.toString()));
                } catch (IOException e) {
                    log.error("Send Error in send message to "+target);
                }
            }
        }
    }

}
