package com.example.chat.service.message;

import com.example.chat.model.Message;
import com.example.chat.model.dto.MessageSendDto;
import com.example.chat.model.enums.MessageState;
import javassist.NotFoundException;

public interface MessageService {

    Message sendMessage(MessageSendDto messageSendDto) throws NotFoundException;

    Boolean seenMessage(long messageId) throws NotFoundException;

    MessageState deliveredMessage(long messageId) throws NotFoundException;
}
