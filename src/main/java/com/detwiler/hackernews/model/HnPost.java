package com.detwiler.hackernews.model;

import com.detwiler.hackernews.server.HnConnection;

public class HnPost {
    private int mPoints;
    private String mPostId;
    private String mOwner;

    public HnPost(final String postId, final String owner, final int points) {
        mPostId = postId;
        mOwner = owner;
        mPoints = points;
    }

    public String getPostId() { return mPostId; }
    public void setPostId(final String id) { mPostId = id; }

    public String getUsername() { return mOwner; }
    public void setUsername(final String user) { mOwner = user; }

    public int getPoints() { return mPoints; }
    public void setPoints(final int points) { mPoints = points; }

    public String getPostUrl() {
        return HnConnection.HN_BASE_URL + "/item?id=" + getPostId();
    }
}
