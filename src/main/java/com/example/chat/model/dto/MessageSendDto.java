package com.example.chat.model.dto;

import com.example.chat.model.Conversation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageSendDto {

    private String message;

    private long senderUserId;

    private long recipientUserId;

    private long conversationId;
}
