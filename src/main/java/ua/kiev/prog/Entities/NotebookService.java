package ua.kiev.prog.Entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.Entities.NotebookContent.Notebook;
import ua.kiev.prog.Entities.NotebookContent.NotebookDAO;
import ua.kiev.prog.Entities.NotebookContent.Page;

import java.util.List;

/**
 * Created by olegb on 30.01.2017.
 */
@Service
public class NotebookService {

    @Autowired
    private NotebookDAO notebookDAO;

    @Transactional
    public Notebook get(long id) {
        return notebookDAO.get(id);
    }

    @Transactional
    public void addNotebook(Notebook notebook) {
        notebookDAO.addNotebook(notebook);
    }

    @Transactional
    public int getPageCount(long id) {
        return notebookDAO.getPageCount(id);
    }

    @Transactional
    public void updatePageCount(long id) {
        notebookDAO.updatePageCount(id);
    }

    @Transactional
    public void deleteNotebook(long[] ids) {
        notebookDAO.deleteNotebook(ids);
    }

    @Transactional
    public List<Notebook> list() {
        return notebookDAO.list();
    }

    @Transactional
    public List<Notebook> list(int num) {
        return notebookDAO.list(num);
    }

    @Transactional
    public List<Notebook> list(String username) {
        return notebookDAO.list(username);
    }

    @Transactional
    public List<Notebook> list(long userId) {
        return notebookDAO.list(userId);
    }

}
