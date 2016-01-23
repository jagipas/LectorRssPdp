package com.pdp.lectorrsspdp;

import org.mcsoxford.rss.RSSItem;

/**
 * Created by Marc on 23/01/2016.
 */
public class RssAdapterItem {
    private RSSItem item;
    private String rssTitle;

    public RssAdapterItem(RSSItem item, String rssTitle) {
        this.item = item;
        this.rssTitle = rssTitle;
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
}
