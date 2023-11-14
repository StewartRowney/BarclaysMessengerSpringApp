package com.example.controllers;

import com.example.services.IMessageService;
import com.example.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MessageControllerSpringTest {

    @Autowired
    MessageController uut;

    @MockBean
    IMessageService mockService;

    @Test
    public void test_GetAllMessages() {
        uut.getAllMessages();
        verify(mockService, times(1)).getAllMessages();
    }
}