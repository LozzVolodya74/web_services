package com.nix.lopachak.service.impl;

import com.nix.lopachak.dao.RoleDao;
import com.nix.lopachak.entity.Role;
import com.nix.lopachak.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Класс содержит набор методов для получения значений с базы данных
 * для объекта типа Role, используя методы интерфейса RoleDao
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /**
     * Метод через RoleDao трансакционно создаёт новую сущность
     *
     * @param role - сущность, которую необходимо создать в базе данных
     */
    @Override
    @Transactional
    public void create(Role role) {
        roleDao.create(role);
    }

    /**
     * Метод через RoleDao трансакционно ищет в базе данных и возвращает одну сущность указанного типа
     *
     * @param id - порядковый номер (primary key) искомой сущности
     * @return - сущность указанного типа
     */
    @Override
    public Role findById(long id) {
        return roleDao.findById(id);
    }

    /**
     * Метод через RoleDao трансакционно обновляет сущность
     *
     * @param role - сущность, которую необходимо обновить в базе данных
     */
    @Override
    @Transactional
    public void update(Role role) {
        roleDao.update(role);
    }

    /**
     * Метод через RoleDao трансакционно удаляет сущность
     *
     * @param role - сущность, которую необходимо удалить в базе данных
     */
    @Override
    @Transactional
    public void remove(Role role) {
        roleDao.remove(role);
    }

    /**
     * Метод через RoleDao трансакционно ищет в базе данных и возвращает все сущности указанного типа
     *
     * @return список всех сущностей указанного типа
     */
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    /**
     * Метод через RoleDao трансакционно находит объект типа Role по её имени
     *
     * @param name - имя объекта типа Role
     * @return - объект типа Role, который хнанится в базе данных
     */
    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
