package com.example.controllers;

import com.example.entities.Person;
import com.example.services.IPersonService;
import com.example.services.PersonService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerNoSpringTest {

    IPersonService mockService = mock(PersonService.class);
    PersonController uut = new PersonController(mockService);

    @Test
    public void test_GetAllPersons() {
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

}