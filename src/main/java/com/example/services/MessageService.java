package com.example.services;

import com.example.entities.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService implements IMessageService{

    public List<Message> getAllMessages() {
        return new ArrayList<>(); //TODO Implement
    }

}
