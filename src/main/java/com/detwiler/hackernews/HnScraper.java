package com.detwiler.hackernews;

import com.detwiler.hackernews.server.HnConnection;
import com.detwiler.hackernews.server.HnLoginDocument;
import com.detwiler.hackernews.server.HnPostDocument;
import com.detwiler.hackernews.server.HnPostListDocument;
import com.detwiler.hackernews.server.HnSession;
import com.detwiler.hackernews.server.HnSessionManager;

import java.io.IOException;

public class HnScraper {
    private HnSessionManager mSessionManager;
    private HnConnection mConnection;

    public HnScraper() {
        mConnection = new HnConnection();
    }

    public HnPostListDocument getPostsForCategory(final HnPostCategory category)
            throws IOException {
        return getConnection().fetchPostList(category);
    }

    public HnPostListDocument getTopPosts() throws IOException {
        return getPostsForCategory(HnPostCategory.TOP);
    }

    public HnPostListDocument getNewPosts() throws IOException {
        return getPostsForCategory(HnPostCategory.NEW);
    }

    public HnPostListDocument getJobPosts() throws IOException {
        return getPostsForCategory(HnPostCategory.JOBS);
    }

    public HnPostDocument getPost(final String id) throws IOException {
        return getConnection().fetchPost(id);
    }

    public HnLoginDocument getLogin(final String username, final String pw) throws IOException {
        return getConnection().fetchLogin();
    }

    public void setCredentialDelegate(final HnSessionManager.CredentialDelegate delegate) {
        mSessionManager = new HnSessionManager(mConnection, delegate);
    }

    public void setActiveUser(final String username) throws AuthenticationException {
        if (mSessionManager == null) {
            throw new AuthenticationException("No CredentialDelegate provided");
        }
        HnSession session = mSessionManager.getSessionForUser(username, false);
        if (session == null) {
            throw new AuthenticationException("Unable to authenticate session");
        }
        getConnection().setSession(session);
    }

    protected HnConnection getConnection() {
        return mConnection;
    }
}
