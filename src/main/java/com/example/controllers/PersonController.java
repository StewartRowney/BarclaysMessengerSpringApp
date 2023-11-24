package com.example.controllers;

import com.example.entities.Person;
import com.example.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@SuppressWarnings("UnusedReturnValue")
@RequestMapping("/persons")
public class PersonController {

    private final IPersonService service;

    @Autowired
    public PersonController(IPersonService service) {
        this.service = service;
    }

    @GetMapping
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

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Person updatePerson(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Person person) {
        return service.updatePerson(person);
    }

    @PatchMapping("/dateOfBirth/{personId}")
    public Person updatePersonDateOfBirth(@PathVariable Long personId, @RequestBody @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime dateOfBirth) {
        return service.updatePersonDateOfBirth(personId, dateOfBirth);
    }

    @DeleteMapping("/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long personId) {
        service.deletePerson(personId);
    }
}
