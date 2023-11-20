package com.example.controllers;

import com.example.entities.Person;
import com.example.services.IPersonService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void test_ServiceCalledFor_AddPerson() throws Exception {
        Person person = new Person("Jim", "Bob", LocalDateTime.of(2000,10,10,14,55));
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(person);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockService, times(1)).addPerson(any(Person.class));
    }
}