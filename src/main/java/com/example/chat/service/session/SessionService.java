package com.example.chat.service.session;

import com.example.chat.model.Session;
import com.example.chat.model.User;

public interface SessionService {
    boolean isUserConnected(User user);

    void connectUser(String userName, String simpSessionId);

    void disconnectUser(String simpSessionId);

    void subscribeUser(String simpSessionId, String simpSubscriptionId, String simpDestination);
}
