package com.example.controllers;

import com.example.entities.Person;
import com.example.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final IPersonService service;

    @Autowired
    public PersonController(IPersonService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Person> getAllPersons() {
        return service.getAllPersons();
    }

    @GetMapping("/{personId}")
    public Person getPerson(@PathVariable Long personId) {
        return service.getPersonById(personId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Person person) {
        return service.addPerson(person);
    }
}
