package com.example.services;

import com.example.data.IMessageRepository;
import com.example.data.IPersonRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    void test_GetAllMessages_ValidRequest() {
        uut.getAllMessages();
        verify(mockMessageRepository, times(1)).findAll();
    }

    @Test
    void test_GetMessage_ValidRequest_InDatabase() {
        Long messageId = 3L;
        uut.getMessage(messageId);
        verify(mockMessageRepository, times(1)).findById(messageId);
    }

    @Test
    void test_AddMessage_ValidRequest_SenderInDatabase() throws JsonProcessingException {
        Person person = getPerson();
        Message message = new Message("Message", person);

        when(mockPersonService.getPersonById(2L)).thenReturn(person);
        uut.addMessage(message);
        verify(mockMessageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void test_AddMessage_ValidRequest_SenderNotInDatabase() throws JsonProcessingException {
        Person person = getPerson();
        Message message = new Message("Message", person);

        when(mockPersonService.getPersonById(2L)).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> uut.addMessage(message));
    }

    @Test
    void test_AddMessage_InvalidRequest_NullSender() {
        Message message = new Message("Message", null);

        assertThrows(ResponseStatusException.class, () -> uut.addMessage(message));
    }

    @Test
    void test_AddMessage_InvalidRequest_SenderWithNullId() {
        Person person = new Person();
        Message message = new Message("Message", person);

        assertThrows(ResponseStatusException.class, () -> uut.addMessage(message));
    }

    @Test
    void test_AddMessage_InvalidRequest_MessageHadId() throws JsonProcessingException {
        Message message = getMessage();

        assertThrows(ResponseStatusException.class, () -> uut.addMessage(message));
    }

    private Person getPerson() throws JsonProcessingException {
        String json = """
                {
                        "firstName": "Stewart",
                        "lastName": "Rowney",
                        "dateOfBirth": "1983-09-02 10:43:23",
                        "id": 2
                    }""";

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return mapper.readValue(json, Person.class);
    }

    private Message getMessage() throws JsonProcessingException {
        String json = """
                {
                        "content": "Message",
                        "sender": {
                            "firstName": "Stewart",
                            "lastName": "Rowney",
                            "dateOfBirth": "1999-08-15 10:30:00",
                            "id": 1
                        },
                        "id": 1
                    }""";

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return mapper.readValue(json, Message.class);
    }
}