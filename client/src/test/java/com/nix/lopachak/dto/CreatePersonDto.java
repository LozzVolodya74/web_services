package com.nix.lopachak.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class -форма для создания нового пользователя с добавлением параметров password
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@ToString
@Getter
@Setter
public class CreatePersonDto extends PersonDto {

    private String password;

    private String passwordAgain;

    public String getPassword() {
        return password;
    }
}
