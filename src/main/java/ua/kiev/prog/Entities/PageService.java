package ua.kiev.prog.Entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.Entities.ImageContent.Image;
import ua.kiev.prog.Entities.NotebookContent.Page;
import ua.kiev.prog.Entities.NotebookContent.PageDAO;

import java.util.List;

/**
 * Created by olegb on 02.02.2017.
 */
@Service
public class PageService {

    @Autowired
    private PageDAO pageDAO;

    @Transactional
    public void setText(long id, String text) {
        pageDAO.setText(id, text);
    }

    @Transactional
    public Page get(long id) {
        return pageDAO.get(id);
    }

    @Transactional
    public Page addPage(Page page) {
        return pageDAO.addPage(page);
    }

    @Transactional
    public void deletePages(long[] ids) {
        pageDAO.deletePages(ids);
    }

    @Transactional
    public int getPageNum(long id) {
        return pageDAO.getPageNum(id);
    }

    @Transactional
    public List<Page> list() {
        return pageDAO.list();
    }

    @Transactional
    public List<Page> list(long notebook_id) {
        return pageDAO.list(notebook_id);
    }

}
