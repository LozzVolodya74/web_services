package com.nix.lopachak.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Class содержит методы для хранения значений Person
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class Person implements Serializable {

    private long id;

    private long roleId;

    private String firstName;

    private String lastName;

    private String login;

    private Date dob;


    private String password;


    private String email;

    private Role role;

    public Person(long id,
                  long roleId,
                  String firstName,
                  String lastName,
                  String login,
                  Date dob,
                  String password,
                  String email) {
        this.id = id;
        this.roleId = roleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.dob = dob;
        this.password = password;
        this.email = email;
    }

    public Person(long roleId, String firstName, String lastName, String login,
                  Date dob, String password, String email) {
        this.roleId = roleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.dob = dob;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person id= " + id + " roleId= " + roleId + " firstName= " + firstName + " lastName= " + lastName
                + " login= " + login + " dob= " + dob + " password= " + password + " email= " + email;
    }

    /**
     * Метод считает и возвращает возраст пользователя
     *
     * @return - возраст пользователя
     */
    public int getAge() {
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        born.setTime(dob);
        return now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
    }

    public Role getRole() {
        return role;
    }

}

