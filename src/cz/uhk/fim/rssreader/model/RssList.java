package cz.uhk.fim.rssreader.model;

import java.util.ArrayList;
import java.util.List;

public class RssList {
    private List<RSSItem> itemList = new ArrayList<>();

    public void addItem(RSSItem i) {
        itemList.add(i);
    }

    public RSSItem getItem(int r) {
        return itemList.get(r);
    }

    public void removeItem(RSSItem r) {
        itemList.remove(r);
    }

    public void removeItem(int i) {
        itemList.remove(i);
    }

    public void removeAll() {
        itemList.clear();
    }

    public List<RSSItem> getAll() {
        return itemList;
    }
}
