package com.example.data;

import com.example.data.IMessageRepository;
import com.example.data.IPersonRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;

@Component
@Profile("!test")
public class Populator {

    private final IMessageRepository messageRepo;
    private final IPersonRepository personRepo;

    @Autowired
    public Populator(IMessageRepository messageRepo, IPersonRepository personRepo) {
        this.messageRepo = messageRepo;
        this.personRepo = personRepo;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void populate() {
        Person person1 = new Person("Stewart", "Rowney", LocalDateTime.of(1999, Month.AUGUST, 15, 10, 30));
        Person person2 = new Person("Jim", "Doherty", LocalDateTime.of(1987, Month.APRIL, 10, 10, 30));
        Person person3 = new Person("Terry", "Bay", LocalDateTime.of(2010, Month.DECEMBER, 10, 10, 30));

        personRepo.save(person1);
        personRepo.save(person2);
        personRepo.save(person3);

        messageRepo.save(new Message("Message", person1));
        messageRepo.save(new Message("Message2", person1));
        messageRepo.save(new Message("Message3", person2));
        messageRepo.save(new Message("Message4", person2));
        messageRepo.save(new Message("Message5", person1));
    }
}
