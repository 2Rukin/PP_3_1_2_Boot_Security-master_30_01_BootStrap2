package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "role_id")
    private Long id;

    @Column(name = "rolename")
    private String roleName;

    @ManyToMany(mappedBy = "rolesSet")
    private Set<User> usersSet = new HashSet<>();


    public Role() {
    }

    public Role(String roleName, Set<User> usersSet) {
        this.roleName = roleName;
        this.usersSet = usersSet;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return id.equals(role.id) && Objects.equals(roleName, role.roleName) && Objects.equals(usersSet, role.usersSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, usersSet);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }
    public String getRoleNameNoPrefix() {
        return roleName.replaceAll("ROLE_","");
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsersSet() {
        return usersSet;
    }

    public void setUsersSet(Set<User> usersSet) {
        this.usersSet = usersSet;
    }
}
