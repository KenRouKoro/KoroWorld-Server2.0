package cn.korostudio.koroworldserver.command;

import cn.hutool.json.JSONObject;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class CommandSource {
    JSONObject source;
    String node;
    String data;
    WebSocketSession session;
}
