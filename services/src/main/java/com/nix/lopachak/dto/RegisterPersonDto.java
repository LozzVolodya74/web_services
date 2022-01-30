package com.nix.lopachak.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * Class -форма для создания нового пользователя с добавлением параметров captcha
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@ToString
@Getter
@Setter
public class RegisterPersonDto extends CreatePersonDto{

    @NotEmpty
    private String captchaText;

    private String captchaImg;
}