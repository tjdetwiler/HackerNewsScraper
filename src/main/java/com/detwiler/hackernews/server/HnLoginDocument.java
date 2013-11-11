package com.detwiler.hackernews.server;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HnLoginDocument extends HnDocument {
    private String mFnid;
    private String mLoginAction;
    private final String mFnidParam = "fnid";
    private final String mUsernameParam = "u";
    private final String mPasswordParam = "p";

    public HnLoginDocument(final HnConnection conn, final Document doc) {
        super(conn, doc);
        parseDocument();
    }

    protected String getFnid() { return mFnid; }
    protected String getLoginUrl() { return mLoginAction; }
    protected String getFnidParam() { return mFnidParam; }
    protected String getUsernameParam() { return mUsernameParam; }
    protected String getPasswordParam() { return mPasswordParam; }

    private void parseDocument() {
        Element fnidElement = getDocument().select("input[name=fnid]").get(0);
        mFnid = fnidElement.attr("value");

        Element formElement = getDocument().select("form").get(0);
        mLoginAction = formElement.attr("action");
    }

    public HnSession submit(final String username, final String pw) {
        StringBuilder url = new StringBuilder();
        url.append("https://");
        url.append(HnConnection.HN_BASE_URL);
        url.append(getLoginUrl());

        Map<String, String> data = new HashMap<String, String>();
        data.put(getUsernameParam(), username);
        data.put(getPasswordParam(), pw);
        data.put(getFnidParam(), getFnid());
        try {
            Connection conn = Jsoup.connect(url.toString());
            conn.data(data);
            conn.method(Connection.Method.POST);
            conn.userAgent("Mozilla/5.0 (X11; Linux x86_64; rv:25.0) Gecko/20100101 Firefox/25.0");
            Connection.Response resp = conn.execute();

            // TODO: validate response
            Document doc = resp.parse();
            HnSession session = new HnSession();
            session.setCookies(resp.cookies());
            return session;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
