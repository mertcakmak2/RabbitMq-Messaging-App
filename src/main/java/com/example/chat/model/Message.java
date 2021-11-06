package com.example.chat.model;

import com.example.chat.model.enums.MessageState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue
    private long id;

    private String message;

    private Boolean isSeen;

    @Enumerated(EnumType.STRING)
    private MessageState state;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "recipient_user_id")
    private User recipientUser;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    private Date sentAt;
}
