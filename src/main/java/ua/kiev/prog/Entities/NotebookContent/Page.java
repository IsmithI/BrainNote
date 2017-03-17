package ua.kiev.prog.Entities.NotebookContent;

import ua.kiev.prog.Entities.ImageContent.Image;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smith on 29.01.17.
 */
@Entity
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images = new ArrayList<>();

    private int page_n;

    public Page() {}

    public Page(Notebook notebook, int page_n) {
        this.notebook = notebook;
        this.page_n = page_n;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public int getPage_n() {
        return page_n;
    }

    public void setPage_n(int page_n) {
        this.page_n = page_n;
    }
}