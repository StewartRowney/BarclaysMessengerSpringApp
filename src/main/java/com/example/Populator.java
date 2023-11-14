package com.example;

import com.example.data.IMessageRepository;
import com.example.data.IPersonRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Populator {

    private IMessageRepository messageRepo;
    private IPersonRepository personRepo;

    public Populator(IMessageRepository messageRepo, IPersonRepository personRepo) {
        this.messageRepo = messageRepo;
        this.personRepo = personRepo;
    }

    public void populate() {
        messageRepo.save(new Message("Populator message"));
        personRepo.save(new Person("Dave", "Dykes", LocalDateTime.MIN));
    }
}
