package com.detwiler.hackernews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data model for a single comment.
 */
public class HnComment extends HnPost {
    private String mText;
    private List<HnComment> mReplies;
    private HnComment mParent;

    /**
     * Deleted comments have almost no information aside from parent/child comments.
     */
    public static HnComment deleted(final HnComment parent) {
        return new HnComment(null, null, null, parent);
    }

    /**
     * Creates a new comment instance.
     * @param text Comment text.
     * @param parent Parent comment (in case of a reply), or null if none.
     */
    public HnComment(final String postId, final String owner, final String text, final HnComment parent) {
        super(postId, owner);
        mReplies = new ArrayList<>();
        mText = text;
        mParent = parent;
    }

    /**
     * Returns the comment text.
     */
    public String getText() { return mText; }

    /**
     * Returns the {@link HnComment} to which this
     * {@link HnComment} is a reply, or {@code null} if this is a top-level comment.
     */
    public HnComment getParent() { return mParent; }

    /**
     * Returns a list of comments that have replied to this comment.
     */
    public List<HnComment> getReplies() { return Collections.unmodifiableList(mReplies); }

    /**
     * Add a reply to this comment. Applications should not call this method.
     */
    public void addReply(final HnComment reply) {
        mReplies.add(reply);
    }

    /**
     * Returns the depth of this comment (0 for top level, 1 for replies to that, etc).
     */
    public int getDepth() {
        int depth = 0;
        HnComment parent = mParent;
        while (parent != null) {
            depth++;
            parent = parent.getParent();
        }
        return depth;
    }
}
