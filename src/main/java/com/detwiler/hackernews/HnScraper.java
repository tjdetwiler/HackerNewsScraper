package com.detwiler.hackernews;

import java.io.IOException;

/**
 * Interface to perform common operations.
 */
public class HnScraper {
    private HnSessionManager mSessionManager;
    private HnConnection mConnection;

    /** Constructs a new {@link com.detwiler.hackernews.HnScraper}. */
    public HnScraper() {
        mConnection = new HnConnection();
    }

    /**
     * Requests the first page of posts for a given category.
     * @param category Page to request posts for.
     * @return An {@link HnPostListDocument} containing the requested data.
     * @throws IOException In case of network error.
     */
    public HnPostListDocument getPostsForCategory(final HnPostCategory category)
            throws IOException {
        return getConnection().fetchPostList(category);
    }

    /**
     * @see {@link #getPost(String)}
     */
    public HnPostDocument getPost(final HnPost post) throws  IOException {
        if (post == null) {
            return null;
        }
        return getPost(post.getPostId());
    }

    /**
     * Requests a single post (with comments) from the server.
     * @param id Post id to download.
     * @return An {@link HnPostDocument} containing the requested data.
     * @throws IOException In case of network error.
     */
    public HnPostDocument getPost(final String id) throws IOException {
        return getConnection().fetchPost(id);
    }

    /**
     * Sets the delegate used to lookup credentials for a user. This is only required if used in conjunction with
     * {@link #setActiveUser(String)}.
     * @param delegate Called to lookup user credentials.
     */
    public void setCredentialDelegate(final HnCredentialDelegate delegate) {
        mSessionManager = new HnSessionManager(mConnection, delegate);
    }

    /**
     * Sets the username to use and attempts to login and obtain a session cookie.
     * @param username Username to pass to the server.
     * @throws HnAuthenticationException An error occurred while authenticating.
     * @see {@link #setCredentialDelegate(HnCredentialDelegate)}
     */
    public void setActiveUser(final String username) throws HnAuthenticationException {
        if (mSessionManager == null) {
            throw new HnAuthenticationException("No CredentialDelegate provided");
        }
        HnSession session = mSessionManager.getSessionForUser(username, false);
        if (session == null) {
            throw new HnAuthenticationException("Unable to authenticate session");
        }
        getConnection().setSession(session);
    }

    protected HnConnection getConnection() {
        return mConnection;
    }
}
