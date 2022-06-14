package cn.korostudio.koroworldserver.wsservice;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.korostudio.koroworldserver.data.DataPackProcess;
import cn.korostudio.koroworldserver.data.sql.SQLDataPackRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketService extends TextWebSocketHandler {
    protected static Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    public static SQLDataPackRepository sqlDataPackRepository;

    @Getter
    protected static ConcurrentHashMap<String , WebSocketSession> Servers=new ConcurrentHashMap<>();

    @Autowired
    public void setSqlDataPackRepository(SQLDataPackRepository sqlDataPackRepository) {
        WebSocketService.sqlDataPackRepository = sqlDataPackRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String serverName = (String)session.getAttributes().get("key");
        logger.info("Server "+serverName+" Connection.");
        Servers.put(serverName,session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String serverName = (String)session.getAttributes().get("key");
        logger.info("Server "+serverName+" Close Connection.");
        Servers.remove(serverName);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        JSONObject dataJSON = JSONUtil.parseObj(message.getPayload());
        dataJSON.putOnce("http",false);
        DataPackProcess.run(dataJSON,session);
    }
}
