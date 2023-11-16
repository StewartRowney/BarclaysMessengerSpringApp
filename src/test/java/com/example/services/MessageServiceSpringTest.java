package com.example.services;

import com.example.data.IMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MessageServiceSpringTest {

    @MockBean
    IMessageRepository mockRepo;

    @Autowired
    MessageService uut;

    @Test
    void test_GetAllMessages() {
        uut.getAllMessages();
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void test_GetMessage() {
        Long messageId = 4L;
        uut.getMessage(messageId);
        verify(mockRepo, times(1)).findById(messageId);
    }

}