package ua.kiev.prog.Entities.NotebookContent;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by smith on 29.01.17.
 */
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;
    private Blob image;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page;

    public Image() {}

    public Image(Page page, Blob image) {
        this.page = page;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Blob getImage() {
        return image;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
