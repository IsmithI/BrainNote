package ua.kiev.prog.Entities.UserContent;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by smith on 29.01.17.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUsers(long[] ids) {
        User u;
        for (long id : ids) {
            u = entityManager.getReference(User.class, id);
            entityManager.remove(u);
        }
    }

    @Override
    public User get(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User login(User user, String password) {
        User tmp = entityManager.getReference(User.class, user);
        if (tmp.getPassword().equals(password))
            return tmp;
        else return null;
    }

    @Override
    public User login(long id, String password) {
        User tmp = entityManager.getReference(User.class, id);
        if (tmp.getPassword().equals(password))
            return tmp;
        else return null;
    }

    @Override
    public User login(String login, String password) {
        List<User> users = entityManager.createQuery("SELECT u FROM User u").getResultList();
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean userLoginExists(User user) {
        List<User> users = entityManager.createQuery("SELECT u FROM User u").getResultList();
        for (User u : users) {
            if (u.getLogin().equals(user.getLogin()))
                return true;
        }
        return false;
    }
}
