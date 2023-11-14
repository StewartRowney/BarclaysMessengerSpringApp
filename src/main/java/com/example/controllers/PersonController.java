package com.example.controllers;

import com.example.entities.Person;
import com.example.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
