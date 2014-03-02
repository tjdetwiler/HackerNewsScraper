package com.detwiler.hackernews;

/**
 * Interface to allow the application to provide authentication information for a user.
 */
public interface HnCredentialDelegate {
    /**
     * Provides the password for a user.
     *
     * @param username Username that needs authenticated.
     * @return The users password, or null if it is not known.
     */
    String getPasswordForUser(String username);
}
