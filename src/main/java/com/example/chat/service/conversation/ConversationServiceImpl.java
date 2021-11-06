package com.example.chat.service.conversation;

import com.example.chat.model.Conversation;
import com.example.chat.repository.ConversationRepository;
import com.example.chat.service.user.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService{

    private final ConversationRepository conversationRepository;
    private final UserService userService;

    @Override
    public Conversation findBySenderUserIdAndRecipientUserId(long senderUserId, long recipientUserId){
        return conversationRepository.findBySenderUserIdAndRecipientUserId(senderUserId, recipientUserId);
    }

    @Override
    public Conversation createConversation(Conversation conversation){
        return conversationRepository.save(conversation);
    }

    @Override
    public Conversation getExistConversation(long senderUserId, long recipientUserId){
        Conversation sendersConversation = findBySenderUserIdAndRecipientUserId(senderUserId, recipientUserId);
        Conversation recipientsConversation = findBySenderUserIdAndRecipientUserId(recipientUserId, senderUserId);
        return sendersConversation != null ? sendersConversation: recipientsConversation;
    }

    @Override
    public List<Conversation> findBySenderUserIdOrRecipientUserId(long userId) throws NotFoundException {

        userService.findUserById(userId);

        return conversationRepository.findBySenderUserIdOrRecipientUserId(userId, userId);
    }
}
