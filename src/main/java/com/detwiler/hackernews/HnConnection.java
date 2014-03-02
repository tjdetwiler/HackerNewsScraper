package com.detwiler.hackernews;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

class HnConnection {
    protected static final String USER_AGENT_FIREFOX25_LINUX =
            "Mozilla/5.0 (X11; Linux x86_64; rv:25.0) Gecko/20100101 Firefox/25.0";
    public static final String HN_BASE_URL = "news.ycombinator.com";

    private HnSession mSession;

    public HnConnection() {
    }

    public void setSession(final HnSession session) {
        mSession = session;
    }

    public HnSession getSession() {
        return mSession;
    }

    public HnPostListDocument fetchPostList(final HnPostCategory category)
            throws IOException {
        return fetchPostList(category.getUrl(), category);
    }

    public HnPostListDocument fetchPostList(final String url, final HnPostCategory category)
            throws IOException {
        String fullUrl = url;
        if (!fullUrl.startsWith("/")) {
            fullUrl = "/" + fullUrl;
        }
        fullUrl = "http://" + HN_BASE_URL + fullUrl;
        final Document document = get(fullUrl);
        return new HnPostListDocument(this, document, category);
    }

    public HnPostDocument fetchPost(final String id) throws IOException {
        final String url = getUrlForPost(id);
        final Document document = get(url);
        return new HnPostDocument(this, document);
    }

    public HnLoginDocument fetchLogin() throws IOException {
        final String url = "https://" + HnConnection.HN_BASE_URL + "/login";
        return new HnLoginDocument(this, get(url));
    }

    protected Document get(final String url) throws IOException {
        final Connection conn = Jsoup.connect(url);
        if (getSession() != null) {
            conn.cookies(getSession().getSessionCookies());
        }
        conn.method(Connection.Method.GET);
        conn.userAgent(getUserAgent());
        return conn.execute().parse();
    }

    protected String getUserAgent() {
        return USER_AGENT_FIREFOX25_LINUX;
    }

    private String getUrlForPost(final String id) {
        return "https://" + HnConnection.HN_BASE_URL + "/item?id=" + id;
    }
}
