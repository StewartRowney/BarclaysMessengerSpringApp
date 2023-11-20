package com.example.services;

import com.example.data.IPersonRepository;
import com.example.entities.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    IPersonRepository mockRepo = mock(IPersonRepository.class);
    PersonService uut = new PersonService(mockRepo);

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
    void test_AddPerson_Valid() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        uut.addPerson(person);
        verify(mockRepo, times(1)).save(person);
    }

    @Test
    void test_AddPerson_NotValid() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        uut.addPerson(person);
        verify(mockRepo, times(1)).save(person);
    }
}