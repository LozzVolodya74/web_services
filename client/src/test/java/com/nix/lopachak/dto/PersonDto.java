package com.nix.lopachak.dto;

import com.nix.lopachak.entity.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class -форма для создания нового пользователя
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@ToString
@Getter
@Setter
public abstract class PersonDto {
    private Long id;

    private String isRole;

    private String firstName;

    private String lastName;

    private String login;

    private java.util.Date dob;

    private String email;

    protected abstract String getPassword();

    /**
     * Метод создаёт объект Person с заполненными из формы полями
     *
     * @return - объект Person с заполненными из формы полями
     */
    public final Person toPerson() {
        Person person = new Person(isRole.equals("user") ? 1L : 2L, firstName, lastName, login, dob, getPassword(), email);
        if (id != null) {
            person.setId(id);
        }
        return person;
    }
}
