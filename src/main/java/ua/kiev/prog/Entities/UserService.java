package ua.kiev.prog.Entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.Entities.UserContent.User;
import ua.kiev.prog.Entities.UserContent.UserDAO;

/**
 * Created by smith on 29.01.17.
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public boolean userLoginExists(User user) {
        return userDAO.userLoginExists(user);
    }

    @Transactional
    public boolean userLoginExists(String login) {
        return userDAO.userLoginExists(login);
    }

    @Transactional
    public User get(long id) {
        return userDAO.get(id);
    }

    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Transactional
    public void addUser(UserDetails user) {
        userDAO.addUser(user);
    }

    @Transactional
    public void deleteUsers(long[] ids) {
        userDAO.deleteUsers(ids);
    }

    @Transactional
    public User login(User user, String password) {
        return userDAO.login(user, password);
    }

    @Transactional
    public User login(String login, String password) {
        return userDAO.login(login, password);
    }

    @Transactional
    public User login(long id, String password) {
        return userDAO.login(id, password);
    }



}
