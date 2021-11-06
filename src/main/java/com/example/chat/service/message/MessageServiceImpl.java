package com.example.chat.service.message;

import com.example.chat.model.Conversation;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.model.dto.MessageSendDto;
import com.example.chat.model.enums.MessageState;
import com.example.chat.repository.MessageRepository;
import com.example.chat.service.conversation.ConversationService;
import com.example.chat.service.user.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ConversationService conversationService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Message sendMessage(MessageSendDto messageSendDto) throws NotFoundException {

        User recipientUser = userService.findUserById(messageSendDto.getRecipientUserId());
        User senderUser = userService.findUserById(messageSendDto.getSenderUserId());
        Conversation conversation = null;

        if(messageSendDto.getConversationId() > 0)
            conversation = conversationService.getExistConversation(senderUser.getId(), recipientUser.getId());
        else
            conversation = conversationService
                    .createConversation(Conversation.builder().senderUser(senderUser).recipientUser(recipientUser).build());

        Message message = Message.builder()
                .message(messageSendDto.getMessage())
                .conversation(conversation)
                .recipientUser(recipientUser)
                .senderUser(senderUser)
                .state(MessageState.sent)
                .sentAt(new Date()).build();

        Message savedMessage = messageRepository.save(message);
        if(savedMessage != null)
            rabbitTemplate.convertAndSend("messageExchange", "messageRoutingKey", savedMessage);

        return savedMessage;
    }

    @Override
    public Boolean seenMessage(long messageId) throws NotFoundException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("Message not found"));

        message.setIsSeen(true);
        Message savedMessage = messageRepository.save(message);

        return savedMessage.getIsSeen();
    }

    @Override
    public MessageState deliveredMessage(long messageId) throws NotFoundException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("Message not found"));

        message.setState(MessageState.delivered);
        Message savedMessage = messageRepository.save(message);

        return savedMessage.getState();
    }

}
