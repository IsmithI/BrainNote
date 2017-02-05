package ua.kiev.prog.Entities.NotebookContent;

import java.util.List;

/**
 * Created by olegb on 02.02.2017.
 */
public interface PageDAO {
    Page get(long id);
    Page addPage(Page page);
    void deletePages(long[] ids);
    int getPageNum(long id);
    List<Page> list();
    List<Page> list(long notebook_id);
    void setText(long id, String text);
}
