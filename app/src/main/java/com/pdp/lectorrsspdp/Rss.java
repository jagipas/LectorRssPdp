package com.pdp.lectorrsspdp;

/**
 * Clase Rss
 */
public class Rss {
    private String url;
    private String categoria;

    public Rss(String url, String categoria) {
        this.url = url;
        this.categoria = categoria;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
