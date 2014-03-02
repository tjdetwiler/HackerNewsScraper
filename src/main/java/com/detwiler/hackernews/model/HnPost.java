package com.detwiler.hackernews.model;

import com.detwiler.hackernews.server.HnConnection;

/**
 * A {@link com.detwiler.hackernews.model.HnPost} is anything that has a post id on Hacker News. This can be a
 * "Submission" (ex: link or self post), or a comment.
 */
public abstract class HnPost {
    private String mPostId;
    private String mOwner;

    public HnPost(final String postId, final String owner) {
        mPostId = postId;
        mOwner = owner;
    }

    /**
     * Returns the ID associated with the post.
     */
    public String getPostId() { return mPostId; }

    /**
     * Returns the username of the owner of this post.
     */
    public String getUsername() { return mOwner; }

    /**
     * Returns a URL that can be used to find this post on news.ycombinator.com.
     */
    public String getPostUrl() {
        return HnConnection.HN_BASE_URL + "/item?id=" + getPostId();
    }
}
