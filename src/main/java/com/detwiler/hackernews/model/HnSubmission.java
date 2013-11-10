package com.detwiler.hackernews.model;

import com.detwiler.hackernews.model.HnPost;

public class HnSubmission extends HnPost {
    private String mPostUrl;
    private String mPostTitle;
    private boolean mIsSelfPost;
    private boolean mIsVotingEnabled;

    public HnSubmission(final String postId, final String url, final String title, final String owner,
        final int points) {
        super(postId, owner, points);
        mPostUrl = url;
        mPostTitle = title;
    }

    public String getSumbissionUrl() { return mPostUrl; }
    public void setSubmissionUrl(final String url) { mPostUrl = url; }

    public String getTitle() { return mPostTitle; }
    public void setTitle(final String title) { mPostTitle = title; }

    public boolean isSelfPost() { return mIsSelfPost; }
    public void setSelfPost(final boolean selfPost) { mIsSelfPost = selfPost; }

    public boolean isVotingEnabled() { return mIsVotingEnabled; }
    public void setVotingEnabled(final boolean votingEnabled) { mIsVotingEnabled = votingEnabled; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HnPost{");
        sb.append("id:");
        sb.append(getPostId());
        sb.append(", url:");
        sb.append(getSumbissionUrl());
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
