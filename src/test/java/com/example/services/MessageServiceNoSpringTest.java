package com.example.services;

import com.example.data.IMessageRepository;
import com.example.data.IPersonRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MessageServiceNoSpringTest {

    private final IMessageRepository mockMessageRepository = mock(IMessageRepository.class);
    private final IPersonService mockPersonService = mock(IPersonService.class);
    private final MessageService uut = new MessageService(mockMessageRepository, mockPersonService);

    @Test
    void test_GetAllMessages() {
        uut.getAllMessages();
        verify(mockMessageRepository, times(1)).findAll();
    }

    @Test
    void test_GetMessage() {
        Long messageId = 3L;
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