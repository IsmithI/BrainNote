package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.kiev.prog.Entities.ImageContent.Image;
import ua.kiev.prog.Entities.ImageService;
import ua.kiev.prog.Entities.NotebookContent.Notebook;
import ua.kiev.prog.Entities.NotebookContent.Page;
import ua.kiev.prog.Entities.NotebookService;
import ua.kiev.prog.Entities.PageService;
import ua.kiev.prog.Entities.UserContent.MyUser;
import ua.kiev.prog.Entities.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by olegb on 30.01.2017.
 */
@Controller
public class NotebookController {

    @Autowired
    private UserService userService;
    @Autowired
    private NotebookService notebookService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/notes")
    public String onNotes(Model model, @RequestParam(required = false) String message) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MyUser dbUser = userService.get(user.getUsername());
        List<Notebook> dbNotebooks = notebookService.list(user.getUsername());

        model.addAttribute("notebook_list", dbNotebooks);
        model.addAttribute("login", dbUser.getUsername());
        model.addAttribute("email", dbUser.getEmail());

        if (message != null) {
            model.addAttribute("result", message);
        }

        return "notes";
    }

    @RequestMapping(value = "/notes/create", method = RequestMethod.POST)
    public String onCreateNotebook(Model model, @RequestParam String name) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MyUser dbUser = userService.get(user.getUsername());

        Notebook notebook = new Notebook(name, dbUser);

        notebookService.addNotebook(notebook);

        return "redirect:/notes";
    }


    @RequestMapping("/notes/add_page")
    public String onAddPage(@RequestParam long notebookId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MyUser myUser = userService.get(user.getUsername());
        Notebook notebook = notebookService.get(notebookId);

        if (!notebook.getUser().getUsername().equals(myUser.getUsername()))
            return "/403";

        Page page = new Page(notebook, notebook.getPages().size() + 1);

        pageService.addPage(page);
        notebookService.updatePageCount(notebookId);

        return "redirect:/notes";
    }

    @RequestMapping("/notes/delete_{id}")
    public String onDelete(@PathVariable("id") long notebookId, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MyUser myUser = userService.get(user.getUsername());
        Notebook notebook = notebookService.get(notebookId);

        if (!notebook.getUser().getUsername().equals(myUser.getUsername()))
            return "/403";

        notebookService.deleteNotebook(notebookId);

        List<Notebook> dbNotebooks = notebookService.list(user.getUsername());

        model.asMap().put("notebook_list", dbNotebooks);

        return "redirect:/notes";
    }

    @RequestMapping(value = "/notes/pages", method = RequestMethod.POST)
    public String onListPages(Model model,
                              @RequestParam Long notebook_id) {
        List<ua.kiev.prog.Entities.NotebookContent.Page> pages = new LinkedList<>();
        pages.addAll(notebookService.get(notebook_id).getPages());
        String pages_json = listToJSON(pages);

        model.addAttribute("pages", pages);
        model.addAttribute("pages_json", pages_json);

        return "redirect:/notes";
    }

    @RequestMapping(value = "/notes/save_pages")
    @ResponseBody public String onSavePages(@RequestParam String text,
                                            @RequestParam String id) {
        System.out.println(text);

        pageService.setText(Long.parseLong(id), text);

        return "" + text;
    }

    @RequestMapping("/notes/upload")
    public ModelAndView onUpload(@RequestParam MultipartFile uploadImage,
                                 @RequestParam Long pageId) throws IOException {
        if (uploadImage != null) {
            Page page = pageService.get(pageId);

            Image image = new Image(page, uploadImage.getBytes());

            imageService.uploadImage(image);
        }

        return new ModelAndView("notes");
    }

    @RequestMapping(value = "/notes/image", method = RequestMethod.GET)
    public void onImage(@RequestParam Long imageId,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        Image image = imageService.get(imageId);

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(image.getImage());

        response.getOutputStream().close();
    }


    @RequestMapping(value = "/notes/image/last")
    @ResponseBody public String getLastImageId(
            @RequestParam Long pageId,
            HttpServletResponse response) throws IOException {

        Page page = pageService.get(pageId);
        Image image = imageService.getLastUploadedImage(page);

        return String.valueOf(image.getId());
    }

    @RequestMapping(value = "/notes/changePassword")
    public ModelAndView onPasswordChange(@RequestParam String old_password,
                                         @RequestParam String new_password,
                                         @RequestParam String repeat_password,
                                         HttpServletRequest request,
                                         RedirectAttributes attributes) {
        ModelAndView model = new ModelAndView();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser myUser = userService.get(user.getUsername());

        String result;

        if (!old_password.equals(myUser.getPassword())) {
            result = "Wrong old password!";
            model.setViewName("redirect:/notes?message=" + result);
            return model;
        }
        if (!new_password.equals(repeat_password)) {
            result = "Passwords don't match!";
            model.setViewName("redirect:/notes?message=" + result);
            return model;
        }

        userService.changePassword(myUser.getUsername(), new_password);

        Utils.authenticateUserAndSetSession(myUser, request, authenticationManager);
        result = "Password changed successfully";
        model.setViewName("redirect:/index?message=" + result);

        return model;
    }

    @RequestMapping(value = "/notes/pages/delete", method = RequestMethod.POST)
    public String onPageDelete(@RequestParam long notebookId,
                               @RequestParam(value = "ids[]") long[] ids) {
        pageService.deletePages(notebookId, ids);
        notebookService.updatePageCount(notebookId);

        return "redirect:/notes";
    }

    @RequestMapping(value = "/notes/changeNotebookName", method = RequestMethod.POST)
    public String onNotebookChangeName(@RequestParam String name, @RequestParam long id) {
        notebookService.changeNotebookName(id, name);

        return "redirect:/notes";
    }

    @RequestMapping(value = "/notes/changeNotebookColor", method = RequestMethod.POST)
    @ResponseBody public String onNotebookChangeColor(@RequestParam String color, @RequestParam long id) {
        notebookService.setColor(id, color);

        return "/notes";
    }

    private String listToJSON(List list) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(list);
    }



}
