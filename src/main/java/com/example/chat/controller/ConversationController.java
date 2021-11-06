package com.example.chat.controller;

import com.example.chat.model.Conversation;
import com.example.chat.service.conversation.ConversationService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping(value = "/{userId}")
    public List<Conversation> findAllConversationByUserId(@PathVariable long userId) throws NotFoundException {
        return conversationService.findBySenderUserIdOrRecipientUserId(userId);
    }

}
