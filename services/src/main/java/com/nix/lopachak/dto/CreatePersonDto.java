package com.nix.lopachak.dto;

import com.nix.lopachak.constraints.FieldMatch;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Class -форма для создания нового пользователя с добавлением параметров password
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@ToString
@Getter
@Setter
@FieldMatch(first = "password", second = "passwordAgain", message = "password fields must match")
public class CreatePersonDto extends PersonDto{

    @Size(min = 1, max = 30)
    @NotEmpty
    private String password;

    @Size(min = 1, max = 30)
    @NotEmpty
    private String passwordAgain;

    public String getPassword() {
        return password;
    }
}
