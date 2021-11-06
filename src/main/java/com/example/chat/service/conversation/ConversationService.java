package com.example.chat.service.conversation;

import com.example.chat.model.Conversation;
import javassist.NotFoundException;

import java.util.List;

public interface ConversationService {

    Conversation findBySenderUserIdAndRecipientUserId(long senderUserId, long recipientUserId);

    Conversation createConversation(Conversation conversation);

    Conversation getExistConversation(long senderUserId, long recipientUserId);

    List<Conversation> findBySenderUserIdOrRecipientUserId(long userId) throws NotFoundException;
}
