package ua.kiev.prog.Entities.ImageContent;

import org.springframework.stereotype.Repository;
import ua.kiev.prog.Entities.NotebookContent.Page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by smith on 3/16/17.
 */
@Repository
public class ImageDAOImpl implements ImageDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Image get(Long id) {
        return entityManager.find(Image.class, id);
    }

    @Override
    public List get(Page page) {
        Query query = entityManager.createQuery("SELECT i FROM Image i WHERE i.page = :page ORDER BY i.id DESC ");
        query.setParameter("page", page);

        return query.getResultList();
    }

    @Override
    public void uploadImage(Image image) {
        entityManager.merge(image);
    }

    @Override
    public Image getLastUploadedImage(Page page) {
        System.out.println("Images: " + page.getImages().size());
        Query query = entityManager.createQuery("SELECT i FROM Image i WHERE i.page = :page ORDER BY i.id DESC ");
        query.setParameter("page", page);

        List<Image> images = query.getResultList();

        return images.get(images.size()-1);
    }
}
