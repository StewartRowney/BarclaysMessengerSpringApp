package com.example.integrationtests;

import com.example.entities.Message;
import com.example.entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonHttpRequestIT {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Disabled
    void getAllMessagesTest() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/persons");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Person[] actualPersons = mapper.readValue(response.getEntity().getContent(), Person[].class);

        assertEquals("Stewart", actualPersons[3].getFirstName());
        assertEquals("Ayush", actualPersons[0].getFirstName());
        assertEquals("Thomas", actualPersons[1].getFirstName());
        assertEquals("Abhijeet", actualPersons[2].getFirstName());
    }

}
