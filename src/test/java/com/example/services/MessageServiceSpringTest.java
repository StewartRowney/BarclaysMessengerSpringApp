package com.example.services;

import com.example.data.IMessageRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MessageServiceSpringTest {

    @MockBean
    IMessageRepository mockMessageRepository;

    @MockBean
    IPersonService mockPersonService;

    @Autowired
    MessageService uut;

    @Test
    void test_GetAllMessages() {
        uut.getAllMessages();
        verify(mockMessageRepository, times(1)).findAll();
    }

    @Test
    void test_GetMessage() {
        Long messageId = 4L;
        uut.getMessage(messageId);
        verify(mockMessageRepository, times(1)).findById(messageId);
    }

    @Test
    void test_AddMessage_ValidSender() {
        Person person = new Person();
        person.setId(2L);
        Message message = new Message("Message", person);

        when(mockPersonService.getPersonById(2L)).thenReturn(person);

        uut.addMessage(message);
        verify(mockMessageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void test_AddMessage_NotValidSender() {
        Person person = new Person();
        person.setId(2L);
        Message message = new Message("Message", person);

        when(mockPersonService.getPersonById(2L)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> uut.addMessage(message));
    }

    @Test
    void test_AddMessage_NullSender() {
        Message message = new Message("Message", null);

        assertThrows(ResponseStatusException.class, () -> uut.addMessage(message));
    }

    @Test
    void test_AddMessage_SenderWithNullId() {
        Person person = new Person();
        Message message = new Message("Message", person);

        assertThrows(ResponseStatusException.class, () -> uut.addMessage(message));
    }

}