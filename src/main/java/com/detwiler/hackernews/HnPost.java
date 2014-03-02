package com.detwiler.hackernews;

/**
 * A {@link HnPost} is anything that has a post id on Hacker News. This can be a
 * "Submission" (ex: link or self post), or a comment.
 */
public abstract class HnPost {
    private String mPostId;
    private String mOwner;

    /**
     * Initialize this post.
     * @param postId Post ID on Hacker News.
     * @param owner Username of the author of the comment.
     */
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
