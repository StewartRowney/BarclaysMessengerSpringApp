package com.example.data;

import com.example.data.IMessageRepository;
import com.example.data.IPersonRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Populator {

    private final IMessageRepository messageRepo;
    private final IPersonRepository personRepo;

    @Autowired
    public Populator(IMessageRepository messageRepo, IPersonRepository personRepo) {
        this.messageRepo = messageRepo;
        this.personRepo = personRepo;
    }

    public void populate() {
//        messageRepo.save(new Message("Populator message"));
//        personRepo.save(new Person("Dave", "Dykes", LocalDateTime.MIN));
    }
}
