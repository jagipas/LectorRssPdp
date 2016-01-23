package com.pdp.lectorrsspdp;


import org.mcsoxford.rss.RSSFeed;

public class RssFeedContainer {

    private String categoria;
    private RSSFeed feed;

    public RssFeedContainer(String categoria, RSSFeed feed) {
        this.categoria = categoria;
        this.feed = feed;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public RSSFeed getFeed() {
        return feed;
    }

    public void setFeed(RSSFeed feed) {
        this.feed = feed;
    }
}
