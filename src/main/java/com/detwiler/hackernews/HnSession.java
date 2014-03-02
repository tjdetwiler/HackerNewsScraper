package com.detwiler.hackernews;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class HnSession {
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
