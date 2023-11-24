package com.example.services;

import com.example.data.IMessageRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService{

    private final IMessageRepository messageRepository;
    private final IPersonService personService;

    @Autowired
    public MessageService(IMessageRepository messageRepository, IPersonService personService) {
        this.messageRepository = messageRepository;
        this.personService = personService;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message getMessage(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElse(null);
    }

    @Override
    public List<Message> getMessageBySenderFirstName(String firstName) {
        return messageRepository.findMessageBySenderFirstName(firstName);
    }

    @Override
    public Message addMessage(Message message) {

        if(message.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot set Person id, set value to null");
        }

        if (message.getSender() == null || message.getSender().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not a valid message sender");
        }

        Person sender = personService.getPersonById(message.getSender().getId());
        if (sender == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message sender does not exist");
        }

        return messageRepository.save(message);
    }

}
