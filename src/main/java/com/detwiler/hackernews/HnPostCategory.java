package com.detwiler.hackernews;

public enum HnPostCategory {
    TOP("news"),
    NEW("newest"),
    JOBS("jobs");

    private String mUrl;

    HnPostCategory(final String url) {
        mUrl = "/" + url;
    }

    public String getUrl() {
        return mUrl;
    }
}
