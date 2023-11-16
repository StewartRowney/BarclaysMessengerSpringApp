package com.example.services;

import com.example.data.IMessageRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MessageServiceNoSpringTest {

    private final IMessageRepository mockRepo = mock(IMessageRepository.class);
    private final MessageService uut = new MessageService(mockRepo);

    @Test
    void test_GetAllMessages() {
        uut.getAllMessages();
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void test_GetMessage() {
        Long messageId = 3L;
        uut.getMessage(messageId);
        verify(mockRepo, times(1)).findById(messageId);
    }
}