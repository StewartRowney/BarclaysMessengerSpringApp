package com.example.controllers;

import com.example.services.IMessageService;
import com.example.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
}