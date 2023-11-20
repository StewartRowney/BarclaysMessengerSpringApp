package com.example.controllers;

import com.example.entities.Message;
import com.example.entities.Person;
import com.example.services.IMessageService;
import com.example.services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@WebMvcTest(MessageController.class)
class MessageControllerSpringWebTest {
    @MockBean
    IMessageService mockService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_ServiceCalledFor_GetAllMessages() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages");
        mockMvc.perform(requestBuilder);

        verify(mockService, times(1)).getAllMessages();
    }

    @Test
    void test_GetMessage() throws Exception {
        Long messageId = 3L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/" + messageId);
        mockMvc.perform(requestBuilder);

        verify(mockService, times(1)).getMessage(messageId);
    }

    @Test
    void test_GetMessageFromSenderFirstName() throws Exception {
        String name = "Stewart";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/sender/firstName/" + name);
        mockMvc.perform(requestBuilder);

        verify(mockService, times(1)).getMessageBySenderFirstName(name);
    }

    @Test
    void test_AddMessage() throws Exception {
        Message message = new Message("This is a message", new Person());
        ObjectMapper mapper = new ObjectMapper();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(message))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockService, times(1)).addMessage(any(Message.class));
    }
}