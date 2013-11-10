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

    public HnPostDocument fetchPost(final String id) throws IOException {
        final String url = getUrlForPost(id);
        System.out.println("Connecting to url: " + url);
        final Document document = Jsoup.connect(url).get();
        return new HnPostDocument(document);
    }

    private String getUrlForPost(final String id) {
        return "https://" + HnConnection.HN_BASE_URL + "/item?id=" + id;
    }
}
