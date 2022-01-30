package com.nix.lopachak.util;

import com.nix.lopachak.entity.Person;
import com.nix.lopachak.model.CurrentPerson;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Класс предоставляет утилитные методы для аутетефикации пользователя
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
public class SecurityUtils {

    /**
     * Метод предоставляет основную информацию о пользователе
     *
     * @return - UserDetails - основная информация о ользователе
     */
    public static UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        // создаём текущего пользователя, вошедшего в систему
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal);
        } else {
            return null;
        }
    }

    /**
     * Метод возвращает значение true or false в зависимости от того, залогинен ли пользователь
     *
     * @return true or false
     */
    public static boolean isLoggedIn() {
        return getCurrentUserDetails() != null;
    }

    /**
     * Метод для аутентефикации пользователя
     *
     * @param person - созданный новый объект Person
     */
    public static void authenticate(Person person) {
        CurrentPerson currentPerson = new CurrentPerson(person);
        // аутентификация по параметрах
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(currentPerson, currentPerson.getPassword(), currentPerson.getAuthorities());
        // добавление данных в SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
