package com.example.chat.consumer;

import com.example.chat.model.Message;
import com.example.chat.service.message.MessageService;
import com.example.chat.service.session.SessionService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final SessionService sessionService;
    private final MessageService messageService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeMessageFromQueue(Message message) {
        System.out.println("Consumed Message ====> "+message.toString());

        if(sessionService.isUserConnected(message.getRecipientUser())){
            CompletableFuture.runAsync(() -> {
                try {
                    messageService.deliveredMessage(message.getId());
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            });
        }

        messagingTemplate.convertAndSendToUser(
                String.valueOf(message.getRecipientUser().getId()) ,"/queue/messages", message );
    }
}
