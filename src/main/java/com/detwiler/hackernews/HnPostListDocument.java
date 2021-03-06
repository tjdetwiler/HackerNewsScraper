package com.detwiler.hackernews;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A document representing a list of submissions (Top, Jobs, etc).
 */
public class HnPostListDocument extends HnDocument {
    private static final Pattern SELF_POST_REGEX = Pattern.compile("item\\?id\\=(\\d+)");
    private static final Pattern UPVOTE_ID_REGEX = Pattern.compile("up_(\\d+)");
    private static final Pattern POINTS_REGEX = Pattern.compile("(\\d+) point[s]?");
    private static final Pattern MORE_LINK_TEXT = Pattern.compile("More");

    private HnPostCategory mCategory;
    private List<HnSubmission> mPosts;
    private String mNextPageHref;

    HnPostListDocument(final HnConnection connection, final Document document,
                              final HnPostCategory category) {
        super(connection, document);
        mCategory = category;
        mPosts = parseDocument();
        Element moreHref = getDocument().select("table table").select("a").last();
        Matcher m = MORE_LINK_TEXT.matcher(moreHref.ownText());
        if (m.matches()) {
            mNextPageHref = moreHref.attr("href");
        }
    }

    /** Returns {@code true} if this document has a continuation on the server. */
    public boolean hasMore() {
        return mNextPageHref != null;
    }

    /** Loads the next page via continuation on the server. */
    public HnPostListDocument more() throws IOException {
        if (!hasMore()) {
            return null;
        }
        return getConnection().fetchPostList(mNextPageHref, getCategory());
    }

    public HnPostCategory getCategory() {
        return mCategory;
    }

    public List<HnSubmission> getPosts() {
        return mPosts;
    }

    private HnSubmission parsePost(final Element postRow, final Element commentRow) {
        Elements e;
        final Element td = postRow.select("td.title>a").get(0);
        final String title = td.ownText();
        String url = td.attr("href");
        boolean selfPost = false;
        boolean ycPost = false;

        //
        // Check if this is a self-post. Self-posts use relative URLs and some posts for YC backed
        // companies only have the post id in the url (since voting is disabled on these posts).
        //
        e = postRow.select("div.votearrow");
        if (e.size() == 0) {
            ycPost = true;
        }
        String id = null;
        Matcher match = SELF_POST_REGEX.matcher(url);
        if (match.matches()) {
            selfPost = true;
            id = match.group(1);
            url = "https://" + HnConnection.HN_BASE_URL + "/" + url;
        } else if (!ycPost) {
            final Element a2 = postRow.select("a[id^=up]").get(0);
            Matcher m = UPVOTE_ID_REGEX.matcher(a2.attr("id"));
            if (!m.matches()) {
                System.out.println("No match!");
                System.out.println("id: " + a2.attr("id"));
                System.out.println("re: " + UPVOTE_ID_REGEX.pattern());
                return null;
            }
            id = m.group(1);
        }

        String userId = null;
        e = commentRow.select("a");
        if (!ycPost && e.size() > 0) {
            userId = e.get(0).ownText();
        }

        int points = 0;
        if (!ycPost) {
            e = commentRow.select("span");
            final String pointsText = e.get(0).ownText();
            final Matcher m = POINTS_REGEX.matcher(pointsText);
            if (m.matches()) {
                points = Integer.parseInt(m.group(1));
            } else {
                throw new RuntimeException("error");
            }
        }
        HnSubmission post = new HnSubmission(id, userId, url, title, points);
        post.setVotingEnabled(!ycPost);
        post.setSelfPost(selfPost);
        return post;
    }

    private List<HnSubmission> parseDocument() {
        Elements elements = getDocument().select("table table");
        elements = elements.get(1).select("td.title:first-child");
        ArrayList<HnSubmission> nodes = new ArrayList<>(elements.size());
        for (Element postNode : elements) {
            postNode = postNode.parent();
            final Element commentsNode = (Element) postNode.nextSibling();
            nodes.add(parsePost(postNode, commentsNode));
        }
        return nodes;
    }
}
