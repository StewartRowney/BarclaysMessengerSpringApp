package com.example.controllers;

import com.example.services.IPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
}