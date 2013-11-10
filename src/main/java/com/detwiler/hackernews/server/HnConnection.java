package com.detwiler.hackernews.server;

import com.detwiler.hackernews.HnPostCategory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HnConnection {
    public static final String HN_BASE_URL = "news.ycombinator.com";
    private static HnConnection INSTANCE;
    public static HnConnection getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (HnConnection.class) {
            if (INSTANCE == null) {
                INSTANCE = new HnConnection();
            }
            return INSTANCE;
        }
    }

    public HnConnection() {
    }

    public HnPostListDocument fetchPostList(final HnPostCategory category)
            throws IOException {
        return fetchPostList(category.getUrl(), category);
    }

    public HnPostListDocument fetchPostList(String url, final HnPostCategory category)
            throws IOException {
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        final String fullUrl = "https://" + HN_BASE_URL + url;
        System.out.println("Connecting to url: " + fullUrl);
        final Document document = Jsoup.connect(fullUrl).get();
        return new HnPostListDocument(document, category);
    }
}
