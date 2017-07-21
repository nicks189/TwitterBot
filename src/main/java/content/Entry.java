package content;

import java.net.URL;
import java.util.Date;

/**
 * Created by nicks189 on 7/15/17.
 */
public class Entry {
    private String title;
    private String author;
    private String description;
    private String url;
    private Date date;

    public Entry() {
        author = description = url = title = "";
        date = new Date();
    }

    public Entry(String title, String author, String description, String url, Date date) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.url = url;
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() { return title; }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Date getDate() {
        return date;
    }

}
