package com.example.integrationtests;

import com.example.entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@ActiveProfiles("test")
public class PersonWithMockHttpRequestIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGettingAllPersons() throws Exception {
        Person[] actualPersons = getAllPersons();

        assertEquals("Stewart", actualPersons[0].getFirstName());
        assertEquals("Ayush", actualPersons[1].getFirstName());
        assertEquals("Thomas", actualPersons[2].getFirstName());
        assertEquals("Abhijeet", actualPersons[3].getFirstName());
    }

    @Test
    public void testGettingPerson() throws Exception {

        long personId = 3L;

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/persons/" + personId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Person actualperson = mapper.readValue(contentAsJson, Person.class);

        assertEquals("Ayush", actualperson.getFirstName());
    }

    @Test
    public void testAddPerson() throws Exception {

        int numberOfPersonsBeforeAdd = getAllPersons().length;

        Person person = new Person("Jim", "Bob", LocalDateTime.of(2000,10,10,14,55));
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(person);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Person actualperson = mapper.readValue(contentAsJson, Person.class);
        int numberOfPersonsAfterAdd = getAllPersons().length;

        assertAll(
                () -> assertEquals(person.getFirstName(), actualperson.getFirstName()),
                () -> assertEquals(person.getLastName(), actualperson.getLastName()),
                () -> assertEquals(person.getDateOfBirth(), actualperson.getDateOfBirth()),
                () -> assertEquals(numberOfPersonsBeforeAdd + 1, numberOfPersonsAfterAdd)

        );

    }

    private Person[] getAllPersons() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/persons")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(contentAsJson, Person[].class);
    }

}
