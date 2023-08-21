package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.dao.UserDao;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserDao dao;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserDao userDao, @Lazy PasswordEncoder passwordEncoder) {
        this.dao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return dao.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return dao.listUsers();
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        if (user != null) {
            if (user.getPassword().isEmpty()) {
                user.setPassword(dao.findById(user.getId()).getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            return dao.updateUser(user);
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        dao.removeById(id);
    }
}
