package com.example.services;

import com.example.entities.Person;

import java.util.List;

public interface IPersonService {
    List<Person> getAllPersons();
    Person getPersonById(Long personId);
    Person addPerson(Person person);
}
