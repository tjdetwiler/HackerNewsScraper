package com.detwiler.hackernews;

/**
 * A submission to HackerNews.
 */
public class HnSubmission extends HnPost {
    private String mPostUrl;
    private String mPostTitle;
    private boolean mIsSelfPost;
    private boolean mIsVotingEnabled;
    private int mPoints;

    /**
     *
     * @param url Url that this post links too. Links to the Hacker News page for self posts.
     * @param title Title/headline of this posting.
     * @param points The number of points this submission has received.
     */
    public HnSubmission(final String postId, final String owner, final String url, final String title,
                        final int points) {
        super(postId, owner);
        mPostUrl = url;
        mPostTitle = title;
        mPoints = points;
    }

    /** Returns the link that was submitted. */
    public String getSumbissionUrl() { return mPostUrl; }

    /** Returns the posting title/headline. */
    public String getTitle() { return mPostTitle; }

    /** Returns the number of points this post has received. */
    public int getPoints() { return mPoints; }


    /** Returns {@code true} if this is a self post and not an external link. */
    public boolean isSelfPost() {
        return mIsSelfPost;
    }

    /** Do not use. */
    public void setSelfPost(final boolean selfPost) {
        mIsSelfPost = selfPost;
    }

    /** Returns {@code true} if this post can be voted on. Some posts (ex jobs) cannot be up/down voted. */
    public boolean isVotingEnabled() {
        return mIsVotingEnabled;
    }

    /** Do not use. */
    public void setVotingEnabled(final boolean votingEnabled) {
        mIsVotingEnabled = votingEnabled;
    }

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
