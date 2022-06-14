package cn.korostudio.koroworldserver.wsservice.config;

import cn.hutool.core.codec.Base62;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class ChatInterceptor implements HandshakeInterceptor {
    protected static Logger logger = LoggerFactory.getLogger(ChatInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info("The handshake begins......");
        // 获得请求参数
        Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), CharsetUtil.CHARSET_UTF_8);
        String sid =  paramMap.get("key");
        if (StrUtil.isNotBlank(sid)) {
            // 放入属性域
            attributes.put("key", Base62.decodeStr(sid));
            logger.info("Server：" + Base62.decodeStr(sid) + " Successful handshake!");
            return true;
        }
        logger.info("The server connection is no longer available.");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        logger.info("Handshake complete.");
    }

}