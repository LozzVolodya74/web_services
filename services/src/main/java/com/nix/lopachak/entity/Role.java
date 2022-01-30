package com.nix.lopachak.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class содержит методы для хранения значений Role
 *
 * @author Volodymyr Lopachak
 * @version 1.0 01 October 2021
 */

@Getter
@Setter
@Entity
@Table(name = "role")
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(unique = true, nullable = false)
    private long id;

    @Column(name = "name", unique = true, nullable = false)
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
