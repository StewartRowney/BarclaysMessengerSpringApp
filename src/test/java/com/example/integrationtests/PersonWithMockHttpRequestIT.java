package com.example.integrationtests;

import com.example.entities.Message;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/persons")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Person[] actualPersons = mapper.readValue(contentAsJson, Person[].class);

        assertEquals("Stewart", actualPersons[0].getFirstName());
        assertEquals("Ayush", actualPersons[1].getFirstName());
        assertEquals("Thomas", actualPersons[2].getFirstName());
        assertEquals("Abhijeet", actualPersons[3].getFirstName());
    }

    @Test
    public void testGettingPerson() throws Exception {

        Long personId = 2L;

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

}
