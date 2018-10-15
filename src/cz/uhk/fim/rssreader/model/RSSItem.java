package cz.uhk.fim.rssreader.model;

public class RSSItem {

    private String title, link, description, pudDate, author;

    public RSSItem() {

    }

    public RSSItem(String title, String link, String description, String pudDate, String author) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pudDate = pudDate;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public RSSItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLink() {
        return link;
    }

    public RSSItem setLink(String link) {
        this.link = link;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RSSItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPudDate() {
        return pudDate;
    }

    public RSSItem setPudDate(String pudDate) {
        this.pudDate = pudDate;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public RSSItem setAuthor(String author) {
        this.author = author;
        return this;
    }
}
