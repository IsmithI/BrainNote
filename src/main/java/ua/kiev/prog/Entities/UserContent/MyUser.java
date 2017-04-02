package ua.kiev.prog.Entities.UserContent;

import ua.kiev.prog.Entities.NotebookContent.Notebook;
import ua.kiev.prog.Entities.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by smith on 29.01.17.
 */
@Entity
@Table(name = "users")
public class MyUser {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Id
    private String username;
    private String password;
    private boolean enabled;

    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Notebook> notebooks = new ArrayList<Notebook>();

    @OneToMany(mappedBy = "role_user", fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<Role>();

    public MyUser() {}

    public MyUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        enabled = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public List<Notebook> getNotebooks() {
        return notebooks;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setNotebooks(List<Notebook> notebooks) {
        this.notebooks = notebooks;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
