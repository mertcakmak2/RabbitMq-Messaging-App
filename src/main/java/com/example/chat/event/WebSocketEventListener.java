package com.example.chat.event;

import com.example.chat.service.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

import java.util.ArrayList;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SessionService sessionService;

    @EventListener
    private void handleSessionConnect(SessionConnectEvent event) {
        Map<String, ArrayList> nativeHeaders = (Map<String, ArrayList>) event.getMessage().getHeaders().get("nativeHeaders");
        var user = nativeHeaders.get("user");
        String simpSessionId = (String) event.getMessage().getHeaders().get("simpSessionId");
        String userName = (String) user.get(0);
        sessionService.connectUser(userName, simpSessionId);
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println(event.getSessionId());
        sessionService.disconnectUser(event.getSessionId());
    }

    @EventListener
    private void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        String simpSessionId = (String) event.getMessage().getHeaders().get("simpSessionId");
        String simpSubscriptionId = (String) event.getMessage().getHeaders().get("simpSubscriptionId");
        String simpDestination = (String) event.getMessage().getHeaders().get("simpDestination");

        sessionService.subscribeUser(simpSessionId, simpSubscriptionId, simpDestination);
    }

}

