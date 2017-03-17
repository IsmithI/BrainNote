package ua.kiev.prog.Entities.ImageContent;

import ua.kiev.prog.Entities.NotebookContent.Page;

import javax.persistence.*;

/**
 * Created by smith on 29.01.17.
 */
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 100000)
    private byte[] image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "page_id")
    private Page page;

    public Image() {}

    public Image(Page page, byte[] image) {
        this.page = page;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
