package ua.kiev.prog.Entities.UserContent;

import org.springframework.stereotype.Repository;
import ua.kiev.prog.Entities.NotebookContent.Notebook;
import ua.kiev.prog.Entities.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

/**
 * Created by smith on 29.01.17.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(MyUser user) {
        entityManager.merge(user);
    }

    @Override
    public void addRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public void deleteUser(String username) {
        MyUser u = entityManager.getReference(MyUser.class, username);
        entityManager.remove(u);
    }

    @Override
    public MyUser get(long id) {
        return entityManager.find(MyUser.class, id);
    }

    @Override
    public MyUser get(String username) {
        return entityManager.find(MyUser.class, username);
    }

    @Override
    public MyUser login(MyUser user, String password) {
        MyUser tmp = entityManager.getReference(MyUser.class, user);
        if (tmp.getPassword().equals(password))
            return tmp;
        else return null;
    }

    @Override
    public MyUser login(long id, String password) {
        MyUser tmp = entityManager.getReference(MyUser.class, id);
        if (tmp.getPassword().equals(password))
            return tmp;
        else return null;
    }

    @Override
    public MyUser login(String username, String password) {
        List<MyUser> users = entityManager.createQuery("SELECT u FROM MyUser u").getResultList();
        for (MyUser user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void changePassword(String username, String newPassword) {
        MyUser myUser = entityManager.getReference(MyUser.class, username);
        myUser.setPassword(newPassword);
    }


    @Override
    public boolean userLoginExists(MyUser user) {
        List<MyUser> users = entityManager.createQuery("SELECT u FROM MyUser u").getResultList();
        for (MyUser u : users) {
            if (u.getUsername().equals(user.getUsername()))
                return true;
        }
        return false;
    }

    @Override
    public boolean userLoginExists(String login) {
        return false;
    }
}
