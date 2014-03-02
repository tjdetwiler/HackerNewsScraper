package com.detwiler.hackernews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides a simplified interface to interact with {@link HnPostListDocument}s.
 */
public class HnSubmissionList {
    private HnScraper mScraper;
    private HnPostCategory mCategory;
    private HnPostListDocument mLastLoadedDocument;
    private List<HnSubmission> mSubmissions;

    HnSubmissionList(final HnScraper scraper, final HnPostCategory category) {
        mScraper = scraper;
        mCategory = category;
        mSubmissions = new ArrayList<>();
    }

    /**
     * Pulls another page of submissions from the server.
     */
    public void more() throws IOException {
        if (mLastLoadedDocument == null) {
            mLastLoadedDocument = mScraper.getPostsForCategory(mCategory);
        } else {
            mLastLoadedDocument = mLastLoadedDocument.more();
        }
        mSubmissions.addAll(mLastLoadedDocument.getPosts());
    }

    /**
     * Returns if this list has a continuation link on the server which can be used to extend the dataset.
     */
    public boolean hasMore() {
        return mLastLoadedDocument == null || mLastLoadedDocument.hasMore();
    }

    /**
     * Returns a reference to the backing list for the collection of cached submissions. This reference is guaranteed
     * to be consistent until a call is made to {@link HnSubmissionList#invalidate()}.
     */
    public List<HnSubmission> getSubmissionList() {
        return Collections.unmodifiableList(mSubmissions);
    }

    /**
     * Call to invalidate the cached dataset. This is required continuation links have expired and the dataset must
     * be reset to the first page.
     */
    public void invalidate() {
        mSubmissions.clear();
        mLastLoadedDocument = null;
    }
}
