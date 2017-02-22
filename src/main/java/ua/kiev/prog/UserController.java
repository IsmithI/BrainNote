package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.kiev.prog.Entities.Role;
import ua.kiev.prog.Entities.UserContent.User;
import ua.kiev.prog.Entities.UserService;

import javax.xml.ws.http.HTTPBinding;
import java.util.HashSet;

/**
 * Created by smith on 29.01.17.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView onLogin() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");

        return model;
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView onIndex(@RequestParam(required = false, defaultValue = "") String result) {

        ModelAndView model = new ModelAndView();

        if (!result.equals(""))
            model.addObject("result", result);

        model.setViewName("login");

        return model;
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
            @RequestParam String email
            ) {

        User user = new User(username, password, email);
        Role role = new Role(user, Utils.ROLE_USER);
        user.getRoles().add(role);

        if (userService.userLoginExists(user) || !password.equals(password_repeat))
            return new ModelAndView("register", "result", "Authentication failed!");

        userService.addUser(user);
        userService.addRole(role);

        return new ModelAndView("/register");
    }

//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView onLogin(@RequestParam String login,
//                                @RequestParam String password,
//                                RedirectAttributes redirectAttributes) {
//
//        User user = userService.login(login, password);
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
