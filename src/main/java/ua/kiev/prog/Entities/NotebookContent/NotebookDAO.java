package ua.kiev.prog.Entities.NotebookContent;

import java.util.List;

/**
 * Created by smith on 29.01.17.
 */
public interface NotebookDAO {
    Notebook get(long id);
    void addNotebook(Notebook notebook);
    void deleteNotebook(long[] id);
    int getPageCount(long id);
    void updatePageCount(long id);
    List<Notebook> list();
    List<Notebook> list(String username);
    List<Notebook> list(long userId);
    List<Notebook> list(int num);
}
