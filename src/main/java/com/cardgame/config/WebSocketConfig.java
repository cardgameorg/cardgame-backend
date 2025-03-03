package com.cardgame.config;

import com.cardgame.filters.CustomWebSocketHandler;
import com.cardgame.filters.WebSocketAuthInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {
    private final WebSocketAuthInterceptor webSocketAuthInterceptor;
    private final CustomWebSocketHandler webSocketHandler;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the STOMP endpoint that clients can connect to (sockJS is enabled)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(
                            ServerHttpRequest request,
                            ServerHttpResponse response,
                            WebSocketHandler wsHandler,
                            Map<String, Object> attributes) {
                        if (request instanceof ServletServerHttpRequest) {
                            HttpServletRequest servletRequest =
                                    ((ServletServerHttpRequest) request).getServletRequest();
                            attributes.put("HTTP_REQUEST", servletRequest);
                        }
                        return true;
                    }
                })
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Simple in-memory broker for topics, queues, etc.
        registry.enableSimpleBroker("/topic", "/queue"); // Prefix for message destinations
        registry.setApplicationDestinationPrefixes("/app"); // Prefix for app-specific messages
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // This is where the WebSocketAuthInterceptor is registered
        registration.interceptors(webSocketAuthInterceptor);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws").setAllowedOrigins("*");
    }
}
