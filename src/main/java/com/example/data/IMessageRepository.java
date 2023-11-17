package com.example.data;

import com.example.entities.Message;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface IMessageRepository extends ListCrudRepository<Message, Long> {
    List<Message> findMessageBySenderFirstName(String firstName);
}
