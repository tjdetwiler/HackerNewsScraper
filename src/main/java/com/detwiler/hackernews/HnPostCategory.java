package com.detwiler.hackernews;

/**
 * Category describing the different listings on Hacker News.
 */
public enum HnPostCategory {
    /** The front page. */
    TOP("news"),
    /** New posts. */
    NEW("newest"),
    /** Job postings. */
    JOBS("jobs");

    private String mUrl;

    HnPostCategory(final String url) {
        mUrl = "/" + url;
    }

    public String getUrl() {
        return mUrl;
    }
}
