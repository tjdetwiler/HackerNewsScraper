package com.detwiler.hackernews.server;

import org.jsoup.nodes.Document;

public class HnDocument {
    private Document mDocument;

    public HnDocument(Document doc) {
        mDocument = doc;
    }

    protected Document getDocument() {
        return mDocument;
    }
}
