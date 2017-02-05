package ua.kiev.prog;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegb on 05.02.2017.
 */
public class Utils {

    public static final List<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>(){{
        new SimpleGrantedAuthority("ROLE_USER");
    }};
}
