package com.nix.lopachak.model;

import com.nix.lopachak.entity.Person;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.singletonList;

/**
 * Класс для хранения текущего пользователя с определёнными параметрами
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
public final class CurrentPerson extends User {

    // создания текущего пользователя с ролями
    public CurrentPerson(Person person) {
        super(person.getLogin(), person.getPassword(), singletonList(new SimpleGrantedAuthority(person.getRole().getRole())));
    }
}
