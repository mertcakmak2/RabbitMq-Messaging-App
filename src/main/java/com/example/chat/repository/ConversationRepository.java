package com.example.chat.repository;

import com.example.chat.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Conversation findBySenderUserIdAndRecipientUserId(long senderUserId, long recipientUserId);

    List<Conversation> findBySenderUserIdOrRecipientUserId(long senderUserId, long recipientUserId);
}
