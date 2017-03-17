package ua.kiev.prog.Entities.ImageContent;

import ua.kiev.prog.Entities.NotebookContent.Page;

import java.util.List;

/**
 * Created by smith on 3/16/17.
 */
public interface ImageDAO {
    Image get(Long id);
    List get(Page page);
    void uploadImage(Image image);
    Image getLastUploadedImage(Page page);
}
