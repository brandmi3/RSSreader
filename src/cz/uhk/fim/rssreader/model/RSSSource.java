package cz.uhk.fim.rssreader.model;

public class RSSSource {
    private String name, source;

    public RSSSource() {
    }

    public RSSSource(String name, String source) {
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public RSSSource setName(String name) {
        this.name = name;
        return this;
    }

    public String getSource() {
        return source;
    }

    public RSSSource setSource(String source) {
        this.source = source;
        return this;
    }
}
