package com.example.controllers;

import com.example.entities.Message;
import com.example.services.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SuppressWarnings("UnusedReturnValue")
@RequestMapping("/messages")
public class MessageController {

    private final IMessageService messageService;

    @Autowired
    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{messageId}")
    public Message getMessage(@PathVariable Long messageId) {
        return messageService.getMessage(messageId);
    }

    @GetMapping("/sender/firstName/{name}")
    public List<Message> getMessageBySenderFirstName(@PathVariable String name) {
        return messageService.getMessageBySenderFirstName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message addMessage(@RequestBody Message message) {
        return messageService.addMessage(message);
    }

}
