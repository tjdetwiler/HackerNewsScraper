package com.detwiler.hackernews.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HnComment {
    private String mPostId;
    private String mUserId;
    private String mText;
    private List<HnComment> mReplies;
    private HnComment mParent;

    public HnComment() {
        mReplies = new ArrayList<HnComment>();
    }

    public void addReply(final HnComment reply) {
        mReplies.add(reply);
    }

    public String getUrl() {
        return "/item?id=" + getPostId();
    }

    public String getPostId() { return mPostId; }
    public void setPostId(final String id) { mPostId = id; }

    public String getUsername() { return mUserId; }
    public void setUsername(final String id) { mUserId = id; }

    public String getText() { return mText; }
    public void setText(final String text) { mText = text; }

    public HnComment getParent() { return mParent; }
    public void setParent(final HnComment parent) { mParent = parent; }

    public List<HnComment> getReplies() { return Collections.unmodifiableList(mReplies); }
    public void setReplies(final List<HnComment> replies) {
        mReplies.clear();
        mReplies.addAll(replies);
    }
}
