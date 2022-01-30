package com.nix.lopachak.controller;

import com.nix.lopachak.dto.CreatePersonDto;
import com.nix.lopachak.dto.PersonDto;
import com.nix.lopachak.entity.Person;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;

import static org.junit.Assert.*;

/**
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRestControllerTest {
    // URL по котрому можна обратится к контроллеру
    private final String URI = "http://web-service:8080/persons";

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void test1GetOneByIdSuccess() {
        ResponseEntity<Person> entity = restTemplate.getForEntity(URI + "/3", Person.class);
        Person body = entity.getBody();
        assertNotNull(body);
        assertEquals(3L, body.getId());
        assertEquals(2L, body.getRoleId());
        assertEquals("test2", body.getFirstName());
        assertEquals("test2", body.getLastName());
        assertEquals("test2", body.getLogin());
        assertEquals("Fri Oct 10 00:00:00 UTC 1986", body.getDob().toString());
        assertEquals("password", body.getPassword());
        assertEquals("test2@gmail.com", body.getEmail());
        assertEquals(200, entity.getStatusCodeValue());
    }

    @Test
    public void test2GetOneByIdFailed() {
        try {
            restTemplate.getForEntity(URI + "/90", Person.class);
            fail("Expected 404 status code");
        } catch (HttpClientErrorException exception) {
            assertEquals(404, exception.getRawStatusCode());
        }
    }

    @Test
    public void test3GetAll() {
        ResponseEntity<Person[]> entity = restTemplate.getForEntity(URI, Person[].class);

        Person[] body = entity.getBody();
        assertNotNull(body);
        assertEquals(6, body.length);
        assertEquals(200, entity.getStatusCodeValue());
    }

    @Test
    public void test4Create() {
        CreatePersonDto personDto = new CreatePersonDto();
        personDto.setIsRole("user");
        personDto.setLogin("create");
        personDto.setEmail("create@gmail.com");
        personDto.setFirstName("CreateFirstName");
        personDto.setLastName("CreateLastName");
        personDto.setPassword("password");
        personDto.setDob(Date.valueOf("2011-11-11"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PersonDto> requestBody = new HttpEntity<>(personDto, headers);
        ResponseEntity<Person> result = restTemplate.postForEntity(URI, requestBody, Person.class);

        assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    public void test5DeleteIfFound() {
        try {
            restTemplate.delete(URI + "/7");
        } catch (RuntimeException exception) {
            fail("Delete must be success:" + exception.getMessage());
        }
    }

    @Test
    public void test6DeleteIfNotFound() {
        try {
            restTemplate.delete(URI + "/90");
        } catch (RuntimeException exception) {
            fail("Delete must be success:" + exception.getMessage());
        }
    }

    @Test
    public void test7UpdateSuccess() {
        Person person = new Person(2L, "firstName", "lastName", "login",
                Date.valueOf("2011-11-11"), "password", "email@gmail.com");
        person.setId(2L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> requestBody = new HttpEntity<>(person, headers);
        ResponseEntity<Person> result = restTemplate.exchange(URI + "/2", HttpMethod.PUT, requestBody, Person.class);

        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void test7UpdateFailed400() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> requestBody = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(URI + "/2", HttpMethod.PUT, requestBody, Person.class);
        } catch (HttpClientErrorException exception) {
            assertEquals(400, exception.getRawStatusCode());
        }
    }

    @Test
    public void test8UpdateFailed404() {
        Person person = new Person(2L, "firstName", "lastName", "login",
                Date.valueOf("2011-11-11"), "password", "email@gmail.com");
        person.setId(2L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> requestBody = new HttpEntity<>(person, headers);
        try {
            restTemplate.exchange(URI + "/90", HttpMethod.PUT, requestBody, Person.class);
        } catch (HttpClientErrorException exception) {
            assertEquals(404, exception.getRawStatusCode());
        }
    }
}
