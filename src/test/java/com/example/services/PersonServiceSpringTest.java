package com.example.services;

import com.example.data.IPersonRepository;
import com.example.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class PersonServiceSpringTest {
    @MockBean
    IPersonRepository mockRepo;

    @Autowired
    PersonService uut;

    @Test
    void getAllPersons() {
        uut.getAllPersons();
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void test_GetPerson() {
        Long personId = 2L;
        uut.getPersonById(personId);
        verify(mockRepo, times(1)).findById(personId);
    }

    @Test
    void test_AddPerson() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        uut.addPerson(person);
        verify(mockRepo, times(1)).save(person);
    }

    @Test
    void test_AddPerson_WithPersonId() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        person.setId(10L);
        assertThrows(ResponseStatusException.class,() -> uut.addPerson(person));
    }
}