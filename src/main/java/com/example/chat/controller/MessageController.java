package com.example.chat.controller;

import com.example.chat.model.Message;
import com.example.chat.model.dto.MessageSendDto;
import com.example.chat.service.message.MessageService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping(value = "/send-message")
    public Message sendMessage(@RequestBody MessageSendDto messageSendDto) throws NotFoundException {
        return messageService.sendMessage(messageSendDto);
    }

    @PostMapping(value = "/seen-message/{messageId}")
    public boolean sendMessage(@PathVariable long messageId) throws NotFoundException {
        return messageService.seenMessage(messageId);
    }
}
