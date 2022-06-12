package cn.korostudio.koroworldserver.data;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.korostudio.koroworldserver.wsservice.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DataPackProcess {

    protected static ConcurrentHashMap<String,DataPackProcessor> Processes = new ConcurrentHashMap<>();

    public static void run(String dataPack){
        run(JSONUtil.parseObj(dataPack));
    }

    public static void register(String name,DataPackProcessor processor){
        Processes.put(name,processor);
    }

    public static void run(JSONObject dataPack)  {
        String type = dataPack.getStr("type");
        if (type==null)return;
        if(Processes.containsKey(type)){
            Processes.get(type).run(dataPack);
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
            }else if(target.equals("SERVERONLY")){
                return;
            }else{
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
