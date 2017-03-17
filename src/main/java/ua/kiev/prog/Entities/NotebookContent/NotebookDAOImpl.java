package ua.kiev.prog.Entities.NotebookContent;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by smith on 29.01.17.
 */
@Repository
public class NotebookDAOImpl implements NotebookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Notebook get(long id) {
        return entityManager.find(Notebook.class, id);
    }

    @Override
    public void addNotebook(Notebook notebook) {
        entityManager.merge(notebook);
    }

    @Override
    public void deleteNotebook(long[] id) {
        Notebook notebook;
        for (long i : id) {
            notebook = entityManager.getReference(Notebook.class, i);
            entityManager.remove(notebook);
        }
    }

    @Override
    public void deleteNotebook(long id) {
        Notebook notebook;
        notebook = entityManager.getReference(Notebook.class, id);
        entityManager.remove(notebook);
    }

    @Override
    public int getPageCount(long id) {
        return entityManager.getReference(Notebook.class, id).getPages().size();
    }

    @Override
    public void updatePageCount(long id) {
        Notebook notebook = entityManager.find(Notebook.class, id);
        notebook.setPageNum(notebook.getPages().size());
    }

    @Override
    public List<Notebook> list() {
        return entityManager.createQuery("SELECT n FROM Notebook n").getResultList();
    }

    @Override
    public List<Notebook> list(String username) {
        Query query = entityManager.createQuery("SELECT n FROM Notebook n WHERE n.user.username = :username");
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public List<Notebook> list(long userId) {
        Query query = entityManager.createQuery("SELECT n FROM Notebook n WHERE n.user.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Notebook> list(int num) {
        List<Notebook> tmp = entityManager.createQuery("SELECT n FROM Notebook n").getResultList();
        for (int i = tmp.size(); i > num; i--)
            tmp.remove(i);
        return tmp;
    }
}
