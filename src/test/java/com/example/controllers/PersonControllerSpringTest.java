package com.example.controllers;

import com.example.entities.Person;
import com.example.services.IPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PersonControllerSpringTest {

    @MockBean
    IPersonService mockService;

    @Autowired
    PersonController uut;

    @Test
    void test_GetAllPersons() {
        uut.getAllPersons();
        verify(mockService, times(1)).getAllPersons();
    }

    @Test
    public void test_GetPerson() {
        Long personId = 2L;
        uut.getPerson(personId);
        verify(mockService, times(1)).getPersonById(personId);
    }

    @Test
    public void test_AddPerson() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        uut.addPerson(person);
        verify(mockService, times(1)).addPerson(person);
    }

    @Test
    public void test_UpdatePerson() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        uut.updatePerson(person);
        verify(mockService, times(1)).updatePerson(person);
    }
}