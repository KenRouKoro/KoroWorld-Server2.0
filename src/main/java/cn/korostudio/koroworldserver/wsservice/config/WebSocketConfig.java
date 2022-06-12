package cn.korostudio.koroworldserver.wsservice.config;

import cn.korostudio.koroworldserver.wsservice.WebSocketService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private ChatInterceptor chatInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketService, "/ws")
                .addInterceptors(chatInterceptor)
                .setAllowedOrigins("*");
    }
}
