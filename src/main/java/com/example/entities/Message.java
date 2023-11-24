package com.example.entities;

import jakarta.persistence.*;


@Entity
public class Message {

    //VARIABLES
    @Id
    @GeneratedValue
    private Long id;
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
        return id;
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
}
