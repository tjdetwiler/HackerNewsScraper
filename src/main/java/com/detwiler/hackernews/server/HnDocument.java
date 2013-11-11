package com.detwiler.hackernews.server;

import org.jsoup.nodes.Document;

public class HnDocument {
    private Document mDocument;
    private HnConnection mConnection;

    public HnDocument(final HnConnection conn, final Document doc) {
        mDocument = doc;
        mConnection = conn;
    }

    public Document getDocument() {
        return mDocument;
    }

    public HnConnection getConnection() {
        return mConnection;
    }
}
