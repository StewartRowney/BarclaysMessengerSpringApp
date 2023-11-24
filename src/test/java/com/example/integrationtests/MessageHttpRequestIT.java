package com.example.integrationtests;

import com.example.entities.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MessageHttpRequestIT {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Disabled
    void getAllMessagesTest() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/messages");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Message[] actualMessages = mapper.readValue(response.getEntity().getContent(), Message[].class);

        assertEquals("This is a message", actualMessages[3].getContent());
        assertEquals("hello", actualMessages[0].getContent());
        assertEquals("everyone", actualMessages[1].getContent());
        assertEquals("message", actualMessages[2].getContent());
    }

}
