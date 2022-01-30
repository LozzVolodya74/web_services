package com.nix.lopachak.service;

import com.nix.lopachak.dto.CreatePersonDto;
import com.nix.lopachak.entity.Person;

import java.util.List;

/**
 * Interface содержит набор общих методов для получения значений с базы данных
 * для объекта типа Person
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
public interface PersonService {

    Person create(CreatePersonDto personDto);

    /**
     * Метод обновляет сущность
     *
     * @param entity - сущность, которую необходимо обновить в базе данных
     */
    void update(Person entity);

    /**
     * Метод удаляет сущность
     *
     * @param entity - сущность, которую необходимо удалить в базе данных
     */
    void remove(Person entity);

    /**
     * Метод ищет в базе данных и возвращает все сущности указанного типа
     *
     * @return список всех сущностей указанного типа
     */
    List<Person> findAll();

    /**
     * Метод ищет в базе данных и возвращает одну сущность указанного типа
     *
     * @param id - порядковый номер (primary key) искомой сущности
     * @return - сущность указанного типа
     */
    Person findById(long id);

    /**
     * Метод находит объект типа Person по его email
     *
     * @param email - електронная пошта пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    Person findByEmail(String email);

    /**
     * Метод находит объект типа Person по его login
     *
     * @param login - логин пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    Person findByLogin(String login);
}
