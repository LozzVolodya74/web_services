package com.nix.lopachak.dto;

import com.nix.lopachak.constraints.FieldMatch;
import com.nix.lopachak.entity.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * Class -форма для обновления данных пользователя с добавлением параметров password
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@ToString
@Getter
@Setter
@FieldMatch(first = "password", second = "passwordAgain", message = "password fields must match")
public class UpdatePersonDto extends PersonDto{

    @Size(max = 30)
    private String password;

    @Size(max = 30)
    private String passwordAgain;

    public String getPassword() {
        return password;
    }

    /**
     * Метод создаёт объект PersonDto с заполненными полями
     *
     * @param person - редактируемый объект пользователя
     * @return - объект PersonDto с заполненными полями, данные для которых получены
     * из объекта Person
     */
    public static PersonDto personToDto(Person person) {
        PersonDto personDto = new UpdatePersonDto();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setLogin(person.getLogin());
        personDto.setDob(person.getDob());
        personDto.setEmail(person.getEmail());
        return personDto;
    }
}
