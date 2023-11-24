package com.example.services;

import com.example.entities.Message;
import java.util.List;

public interface IMessageService {

    List<Message> getAllMessages();
    Message getMessage(Long messageId);
    List<Message> getMessageBySenderFirstName(String firstName);
    Message addMessage(Message message);
}
