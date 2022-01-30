package com.nix.lopachak.service.impl;

import com.nix.lopachak.dao.RoleDao;
import com.nix.lopachak.dao.UserDao;
import com.nix.lopachak.dto.CreatePersonDto;
import com.nix.lopachak.entity.Person;
import com.nix.lopachak.entity.Role;
import com.nix.lopachak.model.CurrentPerson;
import com.nix.lopachak.service.PersonService;
import com.nix.lopachak.service.XssFixer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Класс содержит набор методов для получения значений с базы данных
 * для объекта типа Person, используя методы интерфейса UserDao
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@Service
public class PersonServiceImpl implements PersonService, UserDetailsService {
    public static final Logger LOGGER = Logger.getLogger(PersonService.class.getName());

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final XssFixer fixer;

    @Autowired
    public PersonServiceImpl(UserDao userDao, final RoleDao roleDao, final XssFixer fixer) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.fixer = fixer;
    }

    /**
     * Метод через UserDao трансакционно создаёт новую сущность и записывает её в базу данных
     *
     * @param dto - форма для создания нового пользователя
     */
    @Override
    @Transactional
    public Person create(CreatePersonDto dto) {
        Role role = roleDao.findByName(dto.getIsRole());
        if (role == null) {
            throw new IllegalArgumentException("Invalid role: " + dto.getIsRole());
        }
        Person person = new Person(
                role.getId(),
                fixer.fix(dto.getFirstName()),
                fixer.fix(dto.getLastName()),
                fixer.fix(dto.getLogin()),
                dto.getDob(),
                fixer.fix(dto.getPassword()),
                fixer.fix(dto.getEmail())
        );
        person.setRole(role);
        userDao.create(person);
        LOGGER.info("CREATE Person " + person);
        return person;
    }

    /**
     * Метод через UserDao трансакционно обновляет сущность
     *
     * @param entity - сущность, которую необходимо обновить в базе данных
     */
    @Override
    @Transactional
    public void update(Person entity) {
        Person person = findById(entity.getId());
        person.setRole(entity.getRole());
        person.setDob(entity.getDob());
        person.setEmail(entity.getEmail());
        person.setFirstName(entity.getFirstName());
        person.setLastName(entity.getLastName());
        person.setLogin(entity.getLogin());
        if(entity.getPassword() != null && !entity.getPassword().isEmpty()){
            person.setPassword(entity.getPassword());
        }
        LOGGER.info("UPDATE Person " + person);
        userDao.update(person);
    }

    /**
     * Метод через UserDao трансакционно удаляет сущность
     *
     * @param entity - сущность, которую необходимо удалить в базе данных
     */
    @Override
    @Transactional
    public void remove(Person entity) {
        userDao.remove(entity);
    }

    /**
     * Метод через UserDao ищет в базе данных и возвращает все сущности указанного типа
     *
     * @return список всех сущностей указанного типа
     */
    @Override
    public List<Person> findAll() {
        LOGGER.info("FIND all Person");
        return userDao.findAll();
    }

    /**
     * Метод через UserDao трансакционно ищет в базе данных и возвращает одну сущность указанного типа
     *
     * @param id - порядковый номер (primary key) искомой сущности
     * @return - сущность указанного типа
     */
    @Override
    public Person findById(long id) {
        LOGGER.info("FIND Person by id " + id);
        return userDao.findById(id);
    }

    /**
     * Метод через UserDao находит объект типа Person по его email
     *
     * @param email - електронная пошта пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    @Override
    public Person findByEmail(String email) {
        LOGGER.info("FIND Person by email " + email);
        return userDao.findByEmail(email);
    }

    /**
     * Метод через UserDao находит объект типа Person по его login
     *
     * @param login - логин пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    @Override
    public Person findByLogin(String login) {
        LOGGER.info("FIND Person by login " + login);
        return userDao.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = userDao.findByLogin(login);
        if (person == null) {
            LOGGER.info("Exception loadUserByUsername " + login);
            throw new UsernameNotFoundException("Person not found by login: " + login);
        }
        LOGGER.info("loadUserByUsername " + login);
        return new CurrentPerson(person);
    }
}
