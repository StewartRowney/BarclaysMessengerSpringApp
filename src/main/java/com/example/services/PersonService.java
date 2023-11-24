package com.example.services;

import com.example.data.IPersonRepository;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IPersonService{

    private final IPersonRepository repo;

    @Autowired
    public PersonService(IPersonRepository repo) {
        this.repo = repo;
    }

    public List<Person> getAllPersons() {
        return repo.findAll();
    }

    @Override
    public Person getPersonById(Long personId) {
        Optional<Person> person = repo.findById(personId);
        return person.orElse(null);
    }

    @Override
    public Person addPerson(Person person) {
        if (person.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot set Person id, set value to null");
        }
        return repo.save(person);
    }

    @Override
    public Person updatePerson(Person person) {
        if (person == null || person.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person to update must have an Id");
        }

        if (!repo.existsById(person.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person to update does not exist");
        }

        return repo.save(person);
    }

    @Override
    public Person updatePersonDateOfBirth(Long personId, LocalDateTime dateOfBirth) {
        if (personId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person id cannot be null");
        }

        if (!repo.existsById(personId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person to update not found");
        }

        Person person = getPersonById(personId);
        person.setDateOfBirth(dateOfBirth);

        return repo.save(person);
    }

    @Override
    public void deletePerson(Long personId) {
        if (personId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person id cannot be null");
        }

        if (!repo.existsById(personId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person to update does not exist");
        }
        repo.deleteById(personId);
    }
}
