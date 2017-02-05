package ua.kiev.prog.Entities.UserContent;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by smith on 29.01.17.
 */
public interface UserDAO {
    void addUser(User user);
    void addUser(UserDetails user);
    void deleteUsers(long[] ids);
    User get(long id);
    User login(User user, String password);
    User login(long id, String password);
    User login(String login, String password);
    boolean userLoginExists(User user);
    boolean userLoginExists(String login);
}
