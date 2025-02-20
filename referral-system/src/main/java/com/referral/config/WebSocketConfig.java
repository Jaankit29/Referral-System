package com.referral.config;

import com.referral.websocket.EarningsWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(earningsWebSocketHandler(), "/ws/earnings").setAllowedOrigins("*");
    }

    @Bean
    public EarningsWebSocketHandler earningsWebSocketHandler() {
        return new EarningsWebSocketHandler();
    }
}
