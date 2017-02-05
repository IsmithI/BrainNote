package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.kiev.prog.Entities.UserContent.User;
import ua.kiev.prog.Entities.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smith on 29.01.17.
 */
@Controller
@SessionAttributes("current_user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String onIndex(Model model,
                          @RequestParam(required = false, defaultValue = "") String result,
                          @SessionAttribute(value = "current_user", required = false) User user,
                          RedirectAttributes redirectAttributes) {

        if (model.containsAttribute("current_user")) {
            redirectAttributes.addFlashAttribute("current_user", user);
            return "redirect:/notes";
        }

        if (!result.equals(""))
            model.addAttribute("result", result);
        else model.addAttribute("result", "");

        return "index";
    }

    @RequestMapping("/register")
    public String onRegister(Model model,
                             @RequestParam(required = false, defaultValue = "") String result) {
        if (!result.equals(""))
            model.addAttribute("result", result);
        else model.addAttribute("result", "");
        return "register";
    }

    @RequestMapping(value = "/register_submit", method = RequestMethod.POST)
    public ModelAndView onRegisterSubmit(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String email,
            RedirectAttributes redirectAttributes
            ) {

        User user = new User(login, password, email, Utils.userAuthorities);

        if (userService.userLoginExists(user))
            return new ModelAndView("register", "result", "User with this login already exists!");

        userService.addUser(user);
        redirectAttributes.addFlashAttribute("current_user", user);

        return new ModelAndView("redirect:/notes");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView onLogin(@RequestParam String login,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes) {

        User user = userService.login(login, password);

        UserDetails details = new User(login, password, user.getEmail(), Utils.userAuthorities);


        if (user != null) {
            redirectAttributes.addFlashAttribute("current_user", user);
            return new ModelAndView("redirect:/notes");
        }
        else return new ModelAndView("index", "result", "Log in failed!");
    }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String onLogout() {
//
//    }


}
