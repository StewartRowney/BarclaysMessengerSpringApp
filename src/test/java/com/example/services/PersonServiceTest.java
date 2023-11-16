package com.example.services;

import com.example.data.IPersonRepository;
import org.junit.jupiter.api.Test;

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
}