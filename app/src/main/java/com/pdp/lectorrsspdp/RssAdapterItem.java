package com.pdp.lectorrsspdp;

import org.mcsoxford.rss.RSSItem;

import java.util.Comparator;

/**
 * Created by Marc on 23/01/2016.
 */
public class RssAdapterItem {
    private RSSItem item;
    private String rssTitle;
    private String categoria;

    public RssAdapterItem(RSSItem item, String rssTitle, String categoria) {
        this.item = item;
        this.rssTitle = rssTitle;
        this.categoria = categoria;
    }

    public RSSItem getItem() {
        return item;
    }

    public void setItem(RSSItem item) {
        this.item = item;
    }

    public String getRssTitle() {
        return rssTitle;
    }

    public void setRssTitle(String rssTitle) {
        this.rssTitle = rssTitle;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
class RssAdapterItemDateComparator implements Comparator<RssAdapterItem>{

    @Override
    public int compare(RssAdapterItem item1, RssAdapterItem item2) {
        return item2.getItem().getPubDate().compareTo(item1.getItem().getPubDate());
    }
}