package com.example.controllers;

import com.example.services.IPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(PersonController.class)
class PersonControllerSpringWebTest {

    @MockBean
    IPersonService mockService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_ServiceCalledFor_GetAllPersons() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/persons");
        mockMvc.perform(requestBuilder);

        verify(mockService, times(1)).getAllPersons();
    }

    @Test
    void test_ServiceCalledFor_GetPersonById() throws Exception {
        Long personId = 2L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/persons/" + personId);
        mockMvc.perform(requestBuilder);

        verify(mockService, times(1)).getPersonById(personId);
    }
}