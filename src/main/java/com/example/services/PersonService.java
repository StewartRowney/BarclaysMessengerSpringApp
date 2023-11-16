package com.example.services;

import com.example.data.IPersonRepository;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IPersonService{

    private final IPersonRepository repo;

    @Autowired
    public PersonService(IPersonRepository repo) {
        this.repo = repo;
    }

    public List<Person> getAllPersons() {
        return repo.findAll();
    }

    @Override
    public Person getPersonById(Long personId) {
        Optional<Person> person = repo.findById(personId);
        return person.orElse(null);
    }
}
