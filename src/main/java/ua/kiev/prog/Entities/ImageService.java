package ua.kiev.prog.Entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.Entities.ImageContent.Image;
import ua.kiev.prog.Entities.ImageContent.ImageDAO;
import ua.kiev.prog.Entities.NotebookContent.Page;

import java.util.List;

/**
 * Created by smith on 3/16/17.
 */
@Service
public class ImageService {

    @Autowired
    private ImageDAO imageDAO;

    @Transactional
    public Image get(Long id) {
        return imageDAO.get(id);
    }

    @Transactional
    public List get(Page page) {
        return imageDAO.get(page);
    }

    @Transactional
    public void uploadImage(Image image) {
        imageDAO.uploadImage(image);
    }

    @Transactional
    public Image getLastUploadedImage(Page page) {
        return imageDAO.getLastUploadedImage(page);
    }
}
