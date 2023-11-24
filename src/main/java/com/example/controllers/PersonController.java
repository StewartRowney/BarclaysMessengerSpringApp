package com.example.controllers;

import com.example.entities.Person;
import com.example.services.IPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@SuppressWarnings("UnusedReturnValue")
@Tag(name = "Person API")
@RequestMapping("/persons")
public class PersonController {

    private final IPersonService service;

    @Autowired
    public PersonController(IPersonService service) {
        this.service = service;
    }

    @Operation(summary = "Get all persons", description = "Returns a list of all persons")
    @GetMapping
    public List<Person> getAllPersons() {
        return service.getAllPersons();
    }

    @Operation(summary = "Get person by id", description = "Returns a person by id")
    @GetMapping("/{personId}")
    public Person getPerson(@PathVariable Long personId) {
        return service.getPersonById(personId);
    }

    @Operation(summary = "Add a person", description = "Add a person")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Person person) {
        return service.addPerson(person);
    }

    @Operation(summary = "Update a person", description = "Update a person")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Person updatePerson(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Person person) {
        return service.updatePerson(person);
    }

    @Operation(summary = "Update a person's date of birth", description = "Update a person's date of birth")
    @PatchMapping("/dateOfBirth/{personId}")
    public Person updatePersonDateOfBirth(@PathVariable Long personId, @RequestBody @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime dateOfBirth) {
        return service.updatePersonDateOfBirth(personId, dateOfBirth);
    }

    @Operation(summary = "Delete a person by id", description = "Delete a person by id")
    @DeleteMapping("/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long personId) {
        service.deletePerson(personId);
    }
}
