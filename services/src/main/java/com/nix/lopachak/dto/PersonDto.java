package com.nix.lopachak.dto;

import com.nix.lopachak.entity.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

import static com.nix.lopachak.Constants.USER;

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

    @NotEmpty
    private String isRole;

    @Size(min = 2, max = 30)
    @NotEmpty
    private String firstName;

    @Size(min = 2, max = 30)
    @NotEmpty
    private String lastName;

    @Size(min = 1, max = 30)
    @NotEmpty
    private String login;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull
    @Past
    private java.util.Date dob;

    @NotEmpty
    @Email
    private String email;

    protected abstract String getPassword();

    /**
     * Метод создаёт объект Person с заполненными из формы полями
     *
     * @return - объект Person с заполненными из формы полями
     */
    public final Person toPerson() {
        Person person = new Person(isRole.equals(USER) ? 1L : 2L, firstName, lastName, login, dob, getPassword(), email);
        if (id != null) {
            person.setId(id);
        }
        return person;
    }
}
