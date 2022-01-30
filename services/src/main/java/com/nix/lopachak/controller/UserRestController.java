package com.nix.lopachak.controller;

import com.nix.lopachak.dto.CreatePersonDto;
import com.nix.lopachak.entity.Person;
import com.nix.lopachak.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@RestController
public class UserRestController {

    private final PersonService personService;

    @Autowired
    public UserRestController(final PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/persons/{id}", produces = "application/json")
    public ResponseEntity<Person> getOneById(@NotEmpty @PathVariable(name = "id") long id) {
        Person findPerson = personService.findById(id);
        return findPerson != null
                ? new ResponseEntity<>(findPerson, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/persons", produces = "application/json")
    public ResponseEntity<List<Person>> getAll() {
        final List<Person> list = personService.findAll();
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/persons", consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody CreatePersonDto person) {
        personService.create(person);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/persons/{id}")
    public ResponseEntity<?> delete(@NotNull @PathVariable(name = "id") long id) {
        Person deletePerson = personService.findById(id);
        if (deletePerson != null) {
            personService.remove(deletePerson);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/persons/{id}", consumes = "application/json")
    public ResponseEntity<?> update(@NotEmpty @PathVariable(name = "id") long id, @RequestBody Person personDto) {
        if (personDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            final Person person = personService.findById(id);
            if (person != null) {
                person.setRoleId(personDto.getRoleId());
                person.setDob(personDto.getDob());
                person.setEmail(personDto.getEmail());
                person.setFirstName(personDto.getFirstName());
                person.setLastName(personDto.getLastName());
                person.setEmail(personDto.getEmail());
                person.setPassword(personDto.getPassword());
                personService.update(person);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }
}
