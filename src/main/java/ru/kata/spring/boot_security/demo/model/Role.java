package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String role;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }


    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }


    public Role(String role) {
        this.role = role;
    }

    public Role(Long id, String role, Set<User> users) {
        this.id = id;
        this.role = role;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", users=" + users +
                '}';
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public String getShortName() {
        return role.replaceAll("ROLE_","");
    }
}
