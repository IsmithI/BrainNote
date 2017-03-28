package ua.kiev.prog;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import ua.kiev.prog.Entities.UserContent.MyUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Smith on 20.02.2017.
 */
public class Utils {

    public static final String ROLE_USER = "ROLE_USER",
                                ROLE_ADMIN = "ROLE_ADMIN";

    public static void authenticateUserAndSetSession(MyUser user, HttpServletRequest request, AuthenticationManager authenticationManager) {
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    public static final String[] COLORS = {"#9E9E9E", "#f08080", "#2e8b57", "#f5deb3"};
}
