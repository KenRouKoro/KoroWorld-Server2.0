package cn.korostudio.koroworldserver.data;

import cn.hutool.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;

public interface DataPackProcessor {
    String run(JSONObject dataPack, WebSocketSession session);
}
