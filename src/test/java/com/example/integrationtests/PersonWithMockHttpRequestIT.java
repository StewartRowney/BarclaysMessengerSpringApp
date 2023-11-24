package com.example.integrationtests;

import com.example.entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@ActiveProfiles("test")
 class PersonWithMockHttpRequestIT {

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
     void testGettingAllPersons() throws Exception {
        Person[] actualPersons = getAllPersons();

        assertEquals("Stewart", actualPersons[0].getFirstName());
        assertEquals("Ayush", actualPersons[1].getFirstName());
        assertEquals("Thomas", actualPersons[2].getFirstName());
        assertEquals("Abhijeet", actualPersons[3].getFirstName());
    }

    @Test
     void testGettingPerson() throws Exception {

        long personId = 3L;

        Person actualperson = getPersonById(personId);

        assertEquals("Ayush", actualperson.getFirstName());
    }

    @Test
     void testAddPerson() throws Exception {

        int numberOfPersonsBeforeAdd = getAllPersons().length;

        Person person = new Person("Jim", "Bob", LocalDateTime.of(2000,10,10,14,55));
        Person actualperson = addPerson(person);
        int numberOfPersonsAfterAdd = getAllPersons().length;

        assertAll(
                () -> assertEquals(person.getFirstName(), actualperson.getFirstName()),
                () -> assertEquals(person.getLastName(), actualperson.getLastName()),
                () -> assertEquals(person.getDateOfBirth(), actualperson.getDateOfBirth()),
                () -> assertEquals(numberOfPersonsBeforeAdd + 1, numberOfPersonsAfterAdd)

        );

    }

    @Test
     void updatePerson() throws Exception {
        Person personToUpdate = addPerson(new Person("Update", "Me", LocalDateTime.of(2000,10,10,14,55)));
        int numberOfPersonsBeforeUpdate = getAllPersons().length;
        personToUpdate.setFirstName("Updated");
        personToUpdate.setLastName("Person");

        Person updatedPerson = updatePerson(personToUpdate);
        int numberOfPersonsAfterUpdate = getAllPersons().length;

        assertAll(
                () -> assertEquals(personToUpdate.getFirstName(), updatedPerson.getFirstName()),
                () -> assertEquals(personToUpdate.getLastName(), updatedPerson.getLastName()),
                () -> assertEquals(personToUpdate.getId(), updatedPerson.getId()),
                () -> assertEquals(numberOfPersonsBeforeUpdate, numberOfPersonsAfterUpdate)
        );
    }

    @Test
     void deletePerson() throws Exception {
        int numberOfPersonsBeforeDelete = getAllPersons().length;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/persons/5");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        int numberOfPersonsAfterDelete = getAllPersons().length;

        assertEquals(numberOfPersonsBeforeDelete - 1, numberOfPersonsAfterDelete);
    }

    @Test
     void updatePersonDateOfBirth() throws Exception {
        Person originalPerson = getPersonById(4L);
        LocalDateTime updatedDateOfBirth = LocalDateTime.of(2010, Month.MAY,10,13,43);
        String json = mapper.writeValueAsString(updatedDateOfBirth);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/persons/dateOfBirth/" + originalPerson.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Person updatedPerson = mapper.readValue(contentAsJson, Person.class);

        assertAll(
                () -> assertEquals(updatedDateOfBirth, updatedPerson.getDateOfBirth()),
                () -> assertNotEquals(originalPerson.getDateOfBirth(), updatedPerson.getDateOfBirth())
        );
    }

    private Person updatePerson(Person personToUpdate) throws Exception {
        String json = mapper.writeValueAsString(personToUpdate);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Person.class);
    }

    private Person addPerson(Person person) throws Exception {
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
        return mapper.readValue(contentAsJson, Person.class);
    }

    private Person[] getAllPersons() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/persons")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Person[].class);
    }

    private Person getPersonById(long personId) throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/persons/" + personId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Person.class);
    }

}
