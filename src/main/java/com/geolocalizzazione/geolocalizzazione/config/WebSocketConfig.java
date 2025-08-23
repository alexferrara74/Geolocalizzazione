package com.geolocalizzazione.geolocalizzazione.config;

import com.geolocalizzazione.geolocalizzazione.webSocket.WebSocketNotifiche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketNotifiche webSocketNotifiche;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketNotifiche, "/ws/notifiche/veicoli")
                .setAllowedOrigins("*");
    }
}