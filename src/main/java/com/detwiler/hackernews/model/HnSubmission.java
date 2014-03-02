package com.detwiler.hackernews.model;

public class HnSubmission extends HnPost {
    private String mPostUrl;
    private String mPostTitle;
    private boolean mIsSelfPost;
    private boolean mIsVotingEnabled;
    private int mPoints;

    public HnSubmission(final String postId, final String owner, final String url, final String title,
                        final int points) {
        super(postId, owner);
        mPostUrl = url;
        mPostTitle = title;
        mPoints = points;
    }

    public String getSumbissionUrl() { return mPostUrl; }

    public String getTitle() { return mPostTitle; }

    /**
     * Returns the number of points this post has received.
     */
    public int getPoints() { return mPoints; }


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
