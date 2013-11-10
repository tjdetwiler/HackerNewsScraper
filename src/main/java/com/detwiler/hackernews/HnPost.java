package com.detwiler.hackernews;

import com.detwiler.hackernews.server.HnConnection;

public class HnPost {
    private int mPoints;
    private String mPostId;
    private String mPostUrl;
    private String mPostTitle;
    private String mOwner;
    private boolean mIsSelfPost;
    private boolean mIsVotingEnabled;

    public HnPost(final String postId, final String url, final String title, final String owner,
                  final int points) {
        mPostId = postId;
        mPostUrl = url;
        mOwner = owner;
        mPoints = points;
        mPostTitle = title;
    }

    public String getPostId() { return mPostId; }
    public void setPostId(final String id) { mPostId = id; }

    public String getUrl() { return mPostUrl; }
    public void setUrl(final String url) { mPostUrl = url; }

    public String getUsername() { return mOwner; }
    public void setUsername(final String user) { mOwner = user; }

    public String getTitle() { return mPostTitle; }
    public void setTitle(final String title) { mPostTitle = title; }

    public int getPoints() { return mPoints; }
    public void setPoints(final int points) { mPoints = points; }

    public boolean isSelfPost() { return mIsSelfPost; }
    public void setSelfPost(final boolean selfPost) { mIsSelfPost = selfPost; }

    public boolean isVotingEnabled() { return mIsVotingEnabled; }
    public void setVotingEnabled(final boolean votingEnabled) { mIsVotingEnabled = votingEnabled; }

    public String getCommentUrl() {
        return HnConnection.HN_BASE_URL + "/item?id=" + getPostId();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HnPost{");
        sb.append("id:");
        sb.append(getPostId());
        sb.append(", url:");
        sb.append(getUrl());
        sb.append(", user: ");
        sb.append(getUsername());
        sb.append(", title: ");
        sb.append(getTitle());
        sb.append(", points: ");
        sb.append(getPoints());
        sb.append("}");
        return sb.toString();
    }
}
