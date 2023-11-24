package com.example.services;

import com.example.entities.Person;

import java.time.LocalDateTime;
import java.util.List;

public interface IPersonService {
    List<Person> getAllPersons();
    Person getPersonById(Long personId);
    Person addPerson(Person person);
    Person updatePerson(Person person);
    Person updatePersonDateOfBirth(Long personId, LocalDateTime dateOfBirth);
    void deletePerson(Long personId);
}
