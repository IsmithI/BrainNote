package ua.kiev.prog.Entities.UserContent;

import ua.kiev.prog.Entities.Role;

/**
 * Created by smith on 29.01.17.
 */
public interface UserDAO {
    void addUser(User user);
    void addRole(Role role);
    void deleteUsers(long[] ids);
    User get(long id);
    User login(User user, String password);
    User login(long id, String password);
    User login(String username, String password);
    boolean userLoginExists(User user);
    boolean userLoginExists(String username);
}
