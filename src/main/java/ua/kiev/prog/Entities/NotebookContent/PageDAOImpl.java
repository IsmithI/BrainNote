package ua.kiev.prog.Entities.NotebookContent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by olegb on 02.02.2017.
 */
@Repository
public class PageDAOImpl implements PageDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page get(long id) {
        return entityManager.getReference(Page.class, id);
    }

    @Override
    public Page addPage(Page page) {
        entityManager.merge(page);
        return page;
    }

    @Override
    public void deletePages(long[] ids) {
        Page page;
        for (long i : ids) {
            page = entityManager.getReference(Page.class, i);
            entityManager.remove(page);
        }
    }

    @Override
    public int getPageNum(long id) {
        Page page = entityManager.getReference(Page.class, id);
        return page.getPage_n();
    }

    @Override
    public List<Page> list() {
        return entityManager.createQuery("SELECT p FROM Page p").getResultList();
    }

    @Override
    public List<Page> list(long notebook_id) {
        List<Page> pages = entityManager.createQuery("SELECT p FROM Page p").getResultList();
        List<Page> new_pages = new LinkedList<>();
        for (Page page : pages) {
            if(page.getNotebook().getId() == notebook_id) new_pages.add(page);
        }

        return new_pages;
    }

    @Override
    public void setText(long id, String text) {
        Page page = entityManager.getReference(Page.class, id);
        page.setText(text);
        entityManager.merge(page);
    }
}
