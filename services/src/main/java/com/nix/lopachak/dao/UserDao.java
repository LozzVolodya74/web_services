package com.nix.lopachak.dao;

import com.nix.lopachak.entity.Person;

/**
 * Interface содержит набор специфических методов для получения значений
 * с базы данных объектов типа {@link Person}
 *
 * @author Volodymyr Lopachak
 * @version 1.0 01 October 2021
 */
public interface UserDao extends Dao<Person> {

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
