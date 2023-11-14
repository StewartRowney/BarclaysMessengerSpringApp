package com.example.data;

import com.example.entities.Message;
import org.springframework.data.repository.ListCrudRepository;

public interface IMessageRepository extends ListCrudRepository<Message, Long> {

}
