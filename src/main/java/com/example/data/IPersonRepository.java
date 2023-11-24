package com.example.data;

import com.example.entities.Person;
import org.springframework.data.repository.ListCrudRepository;

public interface IPersonRepository extends ListCrudRepository<Person, Long> {

}
