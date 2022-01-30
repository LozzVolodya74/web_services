package com.nix.lopachak.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Class содержит методы для хранения значений Role
 *
 * @author Volodymyr Lopachak
 * @version 1.0 01 October 2021
 */

@Getter
@Setter
@NoArgsConstructor
public class Role implements Serializable {

    private long id;

    private String role;

    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role id= " + id + " role= " + role;
    }
}
