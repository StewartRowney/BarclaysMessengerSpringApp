package com.example.services;

import com.example.entities.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService implements IPersonService{
    public List<Person> getAllPersons() {
        return new ArrayList<>(); //TODO Implement
    }
}
