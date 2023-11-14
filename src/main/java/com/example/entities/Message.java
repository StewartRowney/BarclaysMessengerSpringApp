package com.example.entities;

import jakarta.persistence.*;


@Entity
public class Message {

    //VARIABLES
    @Id
    @GeneratedValue
    private Long Id;

    private String content;

    //CONSTRUCTORS
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

    //SETTERS
    public void setContent(String content) {
        this.content = content;
    }


}
