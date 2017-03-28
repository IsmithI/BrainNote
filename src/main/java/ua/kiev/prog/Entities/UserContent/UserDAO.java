package ua.kiev.prog.Entities.UserContent;

import ua.kiev.prog.Entities.Role;

/**
 * Created by smith on 29.01.17.
 */
public interface UserDAO {
    void addUser(MyUser user);
    void addRole(Role role);
    void deleteUser(String username);
    MyUser get(long id);
    MyUser get(String username);
    MyUser login(MyUser user, String password);
    MyUser login(long id, String password);
    MyUser login(String username, String password);
    void changePassword(String username, String newPassword);
    boolean userLoginExists(MyUser user);
    boolean userLoginExists(String username);
}
