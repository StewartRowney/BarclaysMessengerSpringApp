package com.example.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

    //VARIABLES
    @Id
    @GeneratedValue
    private Long Id;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBirth;
    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages = new ArrayList<>();;

    //CONSTRUCTORS
    public Person(String firstName, String lastName, LocalDateTime dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
    public Person() {
    }

    //GETTERS
    public Long getId() {
        return Id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    //SETTERS
    public void setId(Long id) {
        Id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
