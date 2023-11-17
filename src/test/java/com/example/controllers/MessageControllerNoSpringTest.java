package com.example.controllers;

import com.example.entities.Message;
import com.example.services.IMessageService;
import com.example.services.MessageService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;

class MessageControllerNoSpringTest {

    IMessageService mockService = mock(MessageService.class);
    MessageController uut = new MessageController(mockService);

    @Test
    void test_GetAllMessages() {
        uut.getAllMessages();
        verify(mockService, times(1)).getAllMessages();
    }

    @Test
    void test_GetMessage() {
        Long messageId = 3L;
        uut.getMessage(messageId);
        verify(mockService, times(1)).getMessage(messageId);
    }

    @Test
    void test_GetMessageFromSenderFirstName() {
        String name = "Stewart";
        uut.getMessageBySenderFirstName(name);
        verify(mockService, times(1)).getMessageBySenderFirstName(name);
    }

}