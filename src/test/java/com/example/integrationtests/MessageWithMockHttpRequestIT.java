package com.example.integrationtests;
import com.example.entities.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@ActiveProfiles("test")
public class MessageWithMockHttpRequestIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGettingAllMessages() throws Exception {

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/messages")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Message[] actualMessages = mapper.readValue(contentAsJson, Message[].class);

        assertEquals("This is a message", actualMessages[3].getContent());
        assertEquals("hello", actualMessages[0].getContent());
        assertEquals("everyone", actualMessages[1].getContent());
        assertEquals("message", actualMessages[2].getContent());
    }

    @Test
    public void testGettingMessage() throws Exception {

        Long messageId = 2L;

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/messages/" + messageId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Message actualMessage = mapper.readValue(contentAsJson, Message.class);

        assertEquals("hello", actualMessage.getContent());
    }

    @Test
    public void testGettingMessageUsingSenderFirstName() throws Exception {

        String name = "Stewart";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/messages/sender/firstName/" + name)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Message[] actualMessages = mapper.readValue(contentAsJson, Message[].class);

        assertEquals(3, actualMessages.length);
    }

}
