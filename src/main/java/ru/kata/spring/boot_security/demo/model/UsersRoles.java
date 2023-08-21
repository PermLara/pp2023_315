package ru.kata.spring.boot_security.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsersRoles implements Serializable {
    @Column(name="user_id")
    Long userId;

    @Column(name="roles_id")
    Long rolesId;

    public UsersRoles(Long userId, Long rolesId) {
        this.userId = userId;
        this.rolesId = rolesId;
    }

    public UsersRoles() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRolesId() {
        return rolesId;
    }

    public void setRolesId(Long rolesId) {
        this.rolesId = rolesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersRoles that = (UsersRoles) o;
        return Objects.equals(userId, that.userId) && Objects.equals(rolesId, that.rolesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, rolesId);
    }
}
