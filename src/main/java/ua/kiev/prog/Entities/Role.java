package ua.kiev.prog.Entities;

import ua.kiev.prog.Entities.UserContent.User;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Smith on 20.02.2017.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private User role_user;

    public Long getId() {
        return id;
    }

    public Role() {}

    public Role(User user, String role) {
        this.role = role;
        this.role_user = user;
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

    public User getRole_user() {
        return role_user;
    }

    public void setRole_user(User role_user) {
        this.role_user = role_user;
    }
}