package com.nix.lopachak.dao;

import java.util.List;

/**
 * Interface содержит набор общих методов для получения значений с базы данных
 *
 * @author Volodymyr Lopachak
 * @version 1.0 01 October 2021
 */
public interface Dao<E> {

    /**
     * Метод создаёт новую сущность
     *
     * @param entity - сущность, которую необходимо создать в базе данных
     */
    void create(E entity);

    /**
     * Метод обновляет сущность
     *
     * @param entity - сущность, которую необходимо обновить в базе данных
     */
    void update(E entity);

    /**
     * Метод удаляет сущность
     *
     * @param entity - сущность, которую необходимо удалить в базе данных
     */
    void remove(E entity);

    /**
     * Метод ищет в базе данных и возвращает все сущности указанного типа
     *
     * @return список всех сущностей указанного типа
     */
    List<E> findAll();

    /**
     * Метод ищет в базе данных и возвращает одну сущность указанного типа
     *
     * @param id - порядковый номер (primary key) искомой сущности
     * @return - сущность указанного типа
     */
    E findById(long id);
}
