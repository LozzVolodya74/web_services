package com.nix.lopachak.service;

import com.nix.lopachak.entity.Role;

import java.util.List;

/**
 * Interface содержит набор общих методов для получения значений с базы данных
 * для объекта типа Role
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
public interface RoleService {

    /**
     * Метод создаёт новую сущность
     *
     * @param role - сущность, которую необходимо создать в базе данных
     */
    void create(Role role);

    /**
     * Метод ищет в базе данных и возвращает одну сущность указанного типа
     *
     * @param id - порядковый номер (primary key) искомой сущности
     * @return - сущность указанного типа
     */
    Role findById(long id);

    /**
     * Метод обновляет сущность
     *
     * @param role - сущность, которую необходимо обновить в базе данных
     */
    void update(Role role);

    /**
     * Метод удаляет сущность
     *
     * @param role - сущность, которую необходимо удалить в базе данных
     */
    void remove(Role role);

    /**
     * Метод ищет в базе данных и возвращает все сущности указанного типа
     *
     * @return список всех сущностей указанного типа
     */
    List<Role> findAll();

    /**
     * Метод находит объект типа Role по её имени
     *
     * @param name - имя объекта типа Role
     * @return - объект типа Role, который хнанится в базе данных
     */
    Role findByName(String name);
}
