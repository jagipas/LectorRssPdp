package com.pdp.lectorrsspdp;

import org.mcsoxford.rss.RSSItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor amb les entrades dels rss
 */
public class RssItemContainer {
    private ArrayList<RssAdapterItem> items;

    public RssItemContainer() {
        items = new ArrayList<RssAdapterItem>();
    }

    public void empty() {
        this.items = new ArrayList<RssAdapterItem>();
    }

    public void addItem(RssAdapterItem item){
        this.items.add(item);
    }

    public ArrayList<RssAdapterItem> getItems() {
        return items;
    }
}
