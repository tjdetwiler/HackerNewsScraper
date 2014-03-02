package com.detwiler.hackernews.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HnSession {
    private Map<String, String> mSessionCookies;

    public HnSession() {
        mSessionCookies = new HashMap<>();
    }

    public void setCookies(final Map<String, String> cookies) {
        mSessionCookies.putAll(cookies);
    }

    public Map<String, String> getSessionCookies() {
        return Collections.unmodifiableMap(mSessionCookies);
    }
}
