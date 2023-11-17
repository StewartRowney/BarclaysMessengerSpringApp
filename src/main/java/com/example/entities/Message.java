package com.example.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;


@Entity
public class Message {

    //VARIABLES
    @Id
    @GeneratedValue
    private Long Id;
    private String content;
    @ManyToOne
    private Person sender;

    //CONSTRUCTORS
    public Message(String content, Person sender) {
        this.content = content;
        this.sender = sender;
    }
    public Message(String content) {
        this.content = content;
    }
    public Message() {}

    //GETTERS
    public Long getId() {
        return Id;
    }
    public String getContent() {
        return content;
    }
    public Person getSender() {
        return sender;
    }

    //SETTERS
    public void setContent(String content) {
        this.content = content;
    }
    public void setId(Long id) {
        Id = id;
    }
}
