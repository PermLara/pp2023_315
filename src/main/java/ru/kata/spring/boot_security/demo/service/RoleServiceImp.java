package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleDao dao;

    @Autowired
    public RoleServiceImp(RoleDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return dao.getAllRoles();
    }
}
