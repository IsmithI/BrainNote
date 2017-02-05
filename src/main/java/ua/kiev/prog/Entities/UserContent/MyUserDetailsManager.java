package ua.kiev.prog.Entities.UserContent;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import ua.kiev.prog.Entities.UserService;

import java.util.List;

/**
 * Created by olegb on 05.02.2017.
 */
public class MyUserDetailsManager implements UserDetailsManager {

    private final UserService userService;

    public MyUserDetailsManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        userService.addUser(userDetails);
    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String s) {
        return userService.userLoginExists(s);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
