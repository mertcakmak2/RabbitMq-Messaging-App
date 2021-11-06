package com.example.chat.service.session;

import com.example.chat.model.Session;
import com.example.chat.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean isUserConnected(User user){
        return redisTemplate.hasKey(user.getName());
    }

    @Override
    public void connectUser(String userName, String simpSessionId){

        Session session = Session.builder()
                .userName(userName)
                .simpSessionId(simpSessionId).build();

        System.out.println("Connected user: " + session.getUserName() + " Session Id: " + simpSessionId);
        redisTemplate.opsForValue().set(simpSessionId, session);
    }

    @Override
    public void disconnectUser(String simpSessionId){
        if (redisTemplate.hasKey(simpSessionId)) {
            Session session = (Session) redisTemplate.opsForValue().get(simpSessionId);
            System.out.println("Disconnected user: " + session.getUserName() + " Session Id: " + simpSessionId);

            redisTemplate.delete(simpSessionId);
            redisTemplate.delete(session.getUserName());
        }
    }

    @Override
    public void subscribeUser(String simpSessionId, String simpSubscriptionId, String simpDestination){

        if (redisTemplate.hasKey(simpSessionId)) {
            Session session = (Session) redisTemplate.opsForValue().get(simpSessionId);
            session.setSimpSubscriptionId(simpSubscriptionId);
            session.setSimpDestination(simpDestination);

            System.out.println("Subscribed user: " + session.getUserName() +
                    " Session Id: " + simpSessionId +
                    " Subscription Id: " + simpSubscriptionId +
                    " Simp Destination: " + simpDestination);

            redisTemplate.opsForValue().set(simpSessionId, session);
            redisTemplate.opsForValue().set(session.getUserName(), simpSessionId);
        }

    }
}
