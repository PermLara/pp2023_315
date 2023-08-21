package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("FROM User u", User.class)
                .getResultList();
    }

    @Override
    public void removeById(Long id) {
        removeUser(findById(id));
    }

    @Override
    public void removeUser(User user) {
        try {
            entityManager.remove(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User updateUser(User user) {
        entityManager.merge(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        User u1 = entityManager.createQuery("FROM User u WHERE u.username LIKE :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        System.out.println("findByUsername: найден в userDaoImp " + u1);
        return u1;

    }
}
