package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.kiev.prog.Entities.NotebookContent.Notebook;
import ua.kiev.prog.Entities.NotebookContent.Page;
import ua.kiev.prog.Entities.NotebookService;
import ua.kiev.prog.Entities.PageService;
import ua.kiev.prog.Entities.UserContent.User;
import ua.kiev.prog.Entities.UserService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by olegb on 30.01.2017.
 */
@Controller
@SessionAttributes("current_user")
public class NotebookController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public String onNotes(Model model,
                          @ModelAttribute("current_user") User user) {

        List<Notebook> notebooks = notebookService.list(user.getId());

        model.addAttribute("notebook_list", notebooks);
        model.addAttribute("current_user", user);

        return "notes";
    }

    @RequestMapping(value = "/create_notebook", method = RequestMethod.POST)
    public String onCreateNotebook(Model model,
                                   @RequestParam String name) {

        User current = (User) model.asMap().get("current_user");
        Notebook notebook = new Notebook(name, current);

        notebookService.addNotebook(notebook);

        return "redirect:/notes";
    }


    @RequestMapping("/notes/add_page_{id}")
    public String onAddPage(@PathVariable("id") long notebookId, Model model) {
        Notebook notebook = notebookService.get(notebookId);
        Page page = new Page(notebook, notebook.getPages().size() + 1);

        pageService.addPage(page);
        notebookService.updatePageCount(notebookId);

        return "redirect:/notes";
    }

    @RequestMapping(value = "/notes/pages", method = RequestMethod.POST)
    public String onListPages(Model model,
                              @RequestParam Long notebook_id) {
        List<Page> pages = new LinkedList<>();
        pages.addAll(notebookService.get(notebook_id).getPages());
        String pages_json = listToJSON(pages);

        model.addAttribute("pages", pages);
        model.addAttribute("pages_json", pages_json);

        return "notes";
    }

    @RequestMapping(value = "/notes/save_page", method = RequestMethod.POST)
    public String onSavePages(@RequestParam String text, @RequestParam long id) {
        pageService.setText(id, text);
        return "redirect:/notes";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String onLogout(Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.getFlashAttributes().remove("current_user");
        model.asMap().remove("current_user");
        return "redirect:/";
    }

    private String listToJSON(List list) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(list);
    }

}
