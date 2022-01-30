package com.nix.lopachak.dao.impl;

import com.nix.lopachak.dao.RoleDao;
import com.nix.lopachak.entity.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class содержит методы для манипулирования сущностями типа Role в базе данных
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Метод сохраняет сущность типа Role используя sessionFactory
     *
     * @param role - сущность, которую необходимо сохранить в базе данных
     */
    @Override // save
    public void create(Role role) {
        sessionFactory.getCurrentSession().save(role);
    }

    /**
     * Метод обновляет сущность типа Role в базе данных используя sessionFactory
     *
     * @param role - сущность, которую необходимо обновить в базе данных
     */
    @Override
    public void update(Role role) {
        sessionFactory.getCurrentSession().update(role);
    }

    /**
     * Метод удаляет сущность типа Role используя sessionFactory
     *
     * @param role - сущность, которую необходимо удалить в базе данных
     */
    @Override
    public void remove(Role role) {
        sessionFactory.getCurrentSession().delete(role);
    }

    /**
     * Метод ищет в базе данных и возвращает все сущности типа Role используя sessionFactory
     *
     * @return список всех сущностей типа Role, которые хранятся в базе данных
     */
    @Override
    public List<Role> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Role").list();
    }

    /**
     * Метод ищет в базе данных и возвращает объект типа Role по её ключу используя sessionFactory
     *
     * @param id - порядковый номер (primary key) искомой сущности типа Role
     * @return - сущность типа Role
     */
    @Override
    public Role findById(long id) {
        return sessionFactory.getCurrentSession().find(Role.class, id);
    }

    /**
     * Метод находит объект типа Role по её имени используя sessionFactory
     *
     * @param name - имя объекта типа Role
     * @return - объект типа Role, который хнанится в базе данных
     */
    @Override
    public Role findByName(String name) {
        final String query = "FROM Role where role = :name";
        return (Role) sessionFactory.getCurrentSession().createQuery(query).setParameter("name", name).getSingleResult();
    }
}
