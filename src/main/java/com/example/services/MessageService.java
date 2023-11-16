package com.example.services;

import com.example.data.IMessageRepository;
import com.example.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService{

    private final IMessageRepository repo;

    @Autowired
    public MessageService(IMessageRepository repo) {
        this.repo = repo;
    }

    public List<Message> getAllMessages() {
        return repo.findAll();
    }

    @Override
    public Message getMessage(Long messageId) {
        Optional<Message> message = repo.findById(messageId);
        return message.orElse(null);
    }

}
