package ua.kiev.prog.Entities.NotebookContent;

import ua.kiev.prog.Entities.UserContent.MyUser;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by smith on 29.01.17.
 */
@Entity
@Table(name = "notebooks")
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int pageNum;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private MyUser user;

    @OneToMany(mappedBy = "notebook", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Page> pages = new LinkedList<>();

    public Notebook() {}

    public Notebook(String name, MyUser user) {
        this.name = name;
        this.user = user;
        pageNum = 0;
    }

    public MyUser getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public List<Page> getPages() {
        return pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
