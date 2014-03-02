package com.detwiler.hackernews;

import org.jsoup.nodes.Document;

/**
 * Base class for documents requested from Hacker News.
 */
public class HnDocument {
    private Document mDocument;
    private HnConnection mConnection;

    HnDocument(final HnConnection conn, final Document doc) {
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
