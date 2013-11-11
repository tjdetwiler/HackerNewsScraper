package com.detwiler.hackernews.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages sessions and authentication for users.
 */
public class HnSessionManager {

    /**
     * Interface to allow the application to provide authentication information for a user.
     */
    public interface CredentialDelegate {
        /**
         * Provides the password for a user.
         *
         * @param username Username that needs authenticated.
         * @return The users password, or null if it is not known.
         */
        public String getPasswordForUser(String username);
    }

    private Map<String, HnSession> mActiveSessions;
    private CredentialDelegate mCredentialDelegate;
    private HnConnection mConnection;

    public HnSessionManager(final HnConnection connection,
                            final CredentialDelegate credentialDelegate) {
        mActiveSessions = new HashMap<String, HnSession>();
        mConnection = connection;
        mCredentialDelegate = credentialDelegate;
    }

    /**
     * Retrieves a session for a user.
     *
     * If a session has already been opened for the user, the cached session is returned. If a
     * session cannot be found for the user, a new session is created.
     *
     * @param user The username to get a session for.
     * @param cachedOnly If true, only return a session if it already exists (do not attempt to
     *                   open a new session).
     * @return HnSession object that contains a validated session cookie.
     */
    public HnSession getSessionForUser(final String user, final boolean cachedOnly) {
        if (hasSession(user) || cachedOnly) {
            return getSession(user);
        }
        final String pass = mCredentialDelegate.getPasswordForUser(user);
        if (pass == null) {
            System.out.println("Unable to find password for user '" + user + "'");
            return null;
        }
        try {
            final HnSession session = mConnection.fetchLogin().submit(user, pass);
            putSession(user, session);
            return session;
        } catch (final IOException e) {
            System.out.println("Exception while attemting to create a new session: " + e);
            return null;
        }
    }

    /**
     * <code>cachedOnly</code> defaults to <code>false</code>.
     *
     * @see HnSessionManager#getSessionForUser(String, boolean)
     */
    public HnSession getSessionForUser(final String user) {
        return getSessionForUser(user, false);
    }

    /**
     * Removes a session from the session manager.
     *
     * Does not impact any references to the removed session.
     * @param user The username to invalidate a session for.
     */
    public void invalidateSessionForUser(final String user) {
        mActiveSessions.put(user, null);
    }

    /*
     * Cache data-structure accessors.
     */
    protected boolean hasSession(final String user) {
        return mActiveSessions.containsKey(user);
    }

    protected HnSession getSession(final String user) {
        return mActiveSessions.get(user);
    }

    protected void putSession(final String user, final HnSession session) {
        mActiveSessions.put(user, session);
    }
}
