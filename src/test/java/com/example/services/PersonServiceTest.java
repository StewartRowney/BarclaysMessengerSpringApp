package com.example.services;

import com.example.data.IPersonRepository;
import com.example.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Test
    void test_AddPerson_WithPersonId() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        person.setId(10L);
        assertThrows(ResponseStatusException.class,() -> uut.addPerson(person));
    }

    @Test
    void test_updatePerson_Valid() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        person.setId(1L);
        when(mockRepo.existsById(person.getId())).thenReturn(true);

        uut.updatePerson(person);
        verify(mockRepo, times(1)).save(person);
    }

    @Test
    void test_updatePerson_NotValid() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        when(mockRepo.existsById(person.getId())).thenReturn(false);

        assertThrows(ResponseStatusException.class,() -> uut.updatePerson(person));
    }

    @Test
    void test_updatePerson_NoPersonId() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        assertThrows(ResponseStatusException.class,() -> uut.updatePerson(person));
    }
}