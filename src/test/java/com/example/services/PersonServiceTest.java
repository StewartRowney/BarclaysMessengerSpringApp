package com.example.services;

import com.example.data.IPersonRepository;
import com.example.entities.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    IPersonRepository mockRepo = mock(IPersonRepository.class);
    PersonService uut = new PersonService(mockRepo);

    @Test
    void test_GetAllPersons_ValidRequest() {
        uut.getAllPersons();
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void test_GetPersonById_ValidRequest_InDatabase() {
        Long personId = 2L;
        uut.getPersonById(personId);
        verify(mockRepo, times(1)).findById(personId);
    }

    @Test
    void test_AddPerson_ValidRequest() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        uut.addPerson(person);
        verify(mockRepo, times(1)).save(person);
    }

    @Test
    void test_AddPerson_InvalidRequest_HasId(){
        Person person = getPerson();
        assertThrows(ResponseStatusException.class,() -> uut.addPerson(person));
    }

    @Test
    void test_UpdatePerson_ValidRequest_InDatabase(){
        Person person = getPerson();
        when(mockRepo.existsById(person.getId())).thenReturn(true);
        uut.updatePerson(person);
        verify(mockRepo, times(1)).save(person);
    }

    @Test
    void test_UpdatePerson_ValidRequest_NotInDatabase() {
        Person person = getPerson();
        when(mockRepo.existsById(person.getId())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updatePerson(person));
    }

    @Test
    void test_UpdatePerson_InvalidRequest_HasNoPersonId() {
        Person person = new Person("Jim", "Bob", LocalDateTime.MIN);
        assertThrows(ResponseStatusException.class,() -> uut.updatePerson(person));
    }

    @Test
    void test_DeletePerson_ValidRequest_InDatabase() {
        Long personId = 5L;
        when(mockRepo.existsById(personId)).thenReturn(true);
        uut.deletePerson(personId);
    }

    @Test
    void test_DeletePerson_ValidRequest_NotInDatabase() {
        Long personId = 5L;
        when(mockRepo.existsById(personId)).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.deletePerson(personId));
    }

    @Test
    void test_DeletePerson_InvalidRequest_HasNoId() {
        assertThrows(ResponseStatusException.class,() -> uut.deletePerson(null));
    }

    private Person getPerson() {
        try {
            String json = """
                {
                        "id": 2,
                        "firstName": "Stewart",
                        "lastName": "Rowney",
                        "dateOfBirth": "1983-09-02 10:43:23"
                    }""";

            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return mapper.readValue(json, Person.class);
        }
        catch (JsonProcessingException ex) {
            return new Person();
        }

    }
}