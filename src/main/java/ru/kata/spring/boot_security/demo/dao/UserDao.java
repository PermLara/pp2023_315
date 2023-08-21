package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    List<User> listUsers();

    void removeUser(User user);

    User updateUser(User user);

    User findById(Long id);

    void removeById(Long id);

    User findByUsername(String username);

}
