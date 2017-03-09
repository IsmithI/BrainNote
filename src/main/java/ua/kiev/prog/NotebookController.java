package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kiev.prog.Entities.NotebookContent.Notebook;
import ua.kiev.prog.Entities.NotebookContent.Page;
import ua.kiev.prog.Entities.NotebookService;
import ua.kiev.prog.Entities.PageService;
import ua.kiev.prog.Entities.UserContent.MyUser;
import ua.kiev.prog.Entities.UserService;

import java.util.*;

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
    private PageService pageService;

    @RequestMapping(value = "/notes")
    public String onNotes(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MyUser dbUser = userService.get(user.getUsername());
        List<Notebook> dbNotebooks = notebookService.list(user.getUsername());

        model.addAttribute("notebook_list", dbNotebooks);
        model.addAttribute("login", dbUser.getUsername());


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


    @RequestMapping("/notes/add_page_{id}")
    public String onAddPage(@PathVariable("id") long notebookId, Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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

    @RequestMapping(value = "/notes/save_pages", method = RequestMethod.POST)
    public String onSavePages(@RequestParam String text,
                              @RequestParam Long id) {
        pageService.setText(id, text);

        return "redirect:/notes";
    }

//    @RequestMapping(value = "/notes/save_pages", method = RequestMethod.POST)
//    public String onSavePages(@RequestParam(value = "ids[]", required = false) String[] ids,
//                              @RequestParam(value = "values[]", required = false) String[] values) {
//
//        for(int i = 0; i < ids.length; i++) {
//            pageService.setText(Long.parseLong(ids[i]), values[i]);
//        }
//
//
//        return "/notes";
//    }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String onLogout(Model model, RedirectAttributes redirectAttributes) {
//        redirectAttributes.getFlashAttributes().remove("current_user");
//        model.asMap().remove("current_user");
//        return "redirect:/";
//    }

    private String listToJSON(List list) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(list);
    }

    private class Pages {
        private List<Long> ids;
        private List<String> values;

        public List<Long> getIds() {
            return ids;
        }

        public void setIds(List<Long> ids) {
            this.ids = ids;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }
    }


}
