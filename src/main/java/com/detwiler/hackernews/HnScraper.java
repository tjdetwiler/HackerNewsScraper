package com.detwiler.hackernews;

import com.detwiler.hackernews.server.HnConnection;
import com.detwiler.hackernews.server.HnPostDocument;
import com.detwiler.hackernews.server.HnPostListDocument;

import java.io.IOException;

public class HnScraper {

    public HnPostListDocument getPostsForCategory(final HnPostCategory category)
            throws IOException {
        return HnConnection.getInstance().fetchPostList(category);
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
        return HnConnection.getInstance().fetchPost(id);
    }
}
