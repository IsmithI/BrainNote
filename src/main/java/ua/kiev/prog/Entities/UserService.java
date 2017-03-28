package ua.kiev.prog.Entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.Entities.UserContent.MyUser;
import ua.kiev.prog.Entities.UserContent.UserDAO;

/**
 * Created by smith on 29.01.17.
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public boolean userLoginExists(MyUser user) {
        return userDAO.userLoginExists(user);
    }

    @Transactional
    public boolean userLoginExists(String login) {
        return userDAO.userLoginExists(login);
    }

    @Transactional
    public MyUser get(long id) {
        return userDAO.get(id);
    }

    @Transactional
    public MyUser get(String username) {
        return userDAO.get(username);
    }

    @Transactional
    public void addUser(MyUser user) {
        userDAO.addUser(user);
    }

    @Transactional
    public void addRole(Role role) {
        userDAO.addRole(role);
    }

    @Transactional
    public void deleteUser(String username) {
        userDAO.deleteUser(username);
    }

    @Transactional
    public MyUser login(MyUser user, String password) {
        return userDAO.login(user, password);
    }

    @Transactional
    public MyUser login(String login, String password) {
        return userDAO.login(login, password);
    }

    @Transactional
    public MyUser login(long id, String password) {
        return userDAO.login(id, password);
    }

    @Transactional
    public void changePassword(String username, String newPassword) {
        userDAO.changePassword(username, newPassword);
    }


}
