package com.nix.lopachak.dao.impl;

import com.nix.lopachak.dao.UserDao;
import com.nix.lopachak.entity.Person;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Class содержит методы для манипулирования сущностями типа Person в базе данных
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@Repository
public class PersonDaoImpl implements UserDao{

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Метод записывает новую сущность типа Person используя sessionFactory
     *
     * @param entity - объект типа Person, который необходимо записать в базу данных
     */
    @Override
    public void create(Person entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    /**
     * Метод обновляет сущность типа Person используя sessionFactory
     *
     * @param entity - сущность типа Person, которую необходимо обновить в базе данных
     */
    @Override
    public void update(Person entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    /**
     * Метод удаляет сущность типа Person используя sessionFactory
     *
     * @param entity - сущность типа Person, которую необходимо удалить в базе данных
     */
    @Override
    public void remove(Person entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    /**
     * Метод ищет в базе данных и возвращает все сущности типа Person,
     * котрые хранятся в базе данных используя sessionFactory
     *
     * @return список всех сущностей указанного типа
     */
    @Override
    public List<Person> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Person ").list();
    }

    /**
     * Метод ищет в базе данных и возвращает одну сущность типа Person используя sessionFactory
     *
     * @param id - порядковый номер (primary key) искомой сущности типа Person
     * @return - сущность типа Person
     */
    @Override
    public Person findById(long id) {
        return sessionFactory.getCurrentSession().find(Person.class, id);
    }

    /**
     * Метод находит объект типа Person по его email используя sessionFactory
     *
     * @param email - електронная пошта пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    @Override
    public Person findByEmail(String email) {
        final String query = "FROM Person where email = :email";
        try {
            return (Person) sessionFactory.getCurrentSession().createQuery(query).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Метод находит объект типа Person по его login используя sessionFactory
     *
     * @param login - логин пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    @Override
    public Person findByLogin(String login) {
        final String query = "FROM Person where login = :login";
        try {
            return (Person) sessionFactory.getCurrentSession().createQuery(query).setParameter("login", login).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
