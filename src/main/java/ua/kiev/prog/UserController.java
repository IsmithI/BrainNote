package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.kiev.prog.Entities.Role;
import ua.kiev.prog.Entities.UserContent.MyUser;
import ua.kiev.prog.Entities.UserService;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by smith on 29.01.17.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping({"/index", "/"})
    public String onIndex(Model model, @RequestParam(required = false) String message) {
        if (message != null) {
            model.addAttribute("result", message);
        }

        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String onRegister(Model model,
                             @RequestParam(required = false, defaultValue = "") String result) {
        if (!result.equals(""))
            model.addAttribute("result", result);
        else model.addAttribute("result", "");
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView onRegisterSubmit(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String password_repeat,
            @RequestParam String email,
            HttpServletRequest request
            ) {

        MyUser user = new MyUser(username, password, email);
        Role role = new Role(user, Utils.ROLE_USER);
        user.getRoles().add(role);

//        if (userService.userLoginExists(user) || !password.equals(password_repeat))
//            return new ModelAndView("register", "result", "Authentication failed!");

        userService.addUser(user);
        userService.addRole(role);


        Utils.authenticateUserAndSetSession(user, request, authenticationManager);

        return new ModelAndView("redirect:/notes");
    }

//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView onLogin(@RequestParam String login,
//                                @RequestParam String password,
//                                RedirectAttributes redirectAttributes) {
//
//        MyUser user = userService.login(login, password);
//
//        if (user != null) {
//            redirectAttributes.addFlashAttribute("current_user", user);
//            return new ModelAndView("redirect:/notes");
//        }
//        else return new ModelAndView("index", "result", "Log in failed!");
//    }
//
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String onLogout() {
//
//    }


}
