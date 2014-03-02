package com.detwiler.hackernews.server;

import com.detwiler.hackernews.model.HnComment;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HnPostDocument extends HnDocument {
    private static final Pattern SELF_POST_REGEX = Pattern.compile("item\\?id\\=(\\d+)");
    private static final int REPLY_INDENT = 40;

    private List<HnComment> mComments;

    public HnPostDocument(final HnConnection conn, final Document document) {
        super(conn, document);
        mComments = parsePost();
    }

    public List<HnComment> getComments() {
        return Collections.unmodifiableList(mComments);
    }

    /**
     * Parse a single comment from an {@link org.jsoup.nodes.Element}.
     * @param parents Mapping of depth (parents are 0, their children are 1, etc), to the most recent comment that has
     *                been seen at this depth. Depth is extracted from information contained in {@code commentElemenet}.
     * @param commentElement DOM node to parse the comment from.
     * @return {@link com.detwiler.hackernews.model.HnComment} instance representing this DOM node, or null if it is not
     * valid.
     */
    private HnComment parseComment(final Map<Integer, HnComment> parents,
                                   final Element commentElement) {
        Elements es = commentElement.select("td>img[src=s.gif]");
        final int depth = Integer.parseInt(es.get(0).attr("width")) / REPLY_INDENT;
        HnComment parent = null;
        if (depth > 0) {
            parent = parents.get(depth-1);
        }
        es = commentElement.select("span.comhead>a");
        // Extract user/post id
        if (es.size() == 0) {
            // This element has no children if the comment has been deleted.
            return HnComment.deleted(parent);
        }
        final String commentHref = es.get(1).attr("href");
        final Matcher m = SELF_POST_REGEX.matcher(commentHref);
        if (!m.matches()) {
            System.out.println(commentHref);
            System.exit(-1);
            // ERROR:
        }
        final String userId = es.get(0).text();
        final String postId = m.group(1);
        final String text = commentElement.select("span.comment>font").get(0).text();
        final HnComment comment = new HnComment(postId, userId, text, parent);
        if (parent != null) {
            parent.addReply(comment);
        }
        parents.put(depth, comment);

        return comment;
    }

    private List<HnComment> parsePost() {
        // so. many. tables.
        final Elements commentElements = getDocument().select("table table table");
        final Map<Integer, HnComment> parents = new HashMap<Integer, HnComment>();
        final List<HnComment> comments = new ArrayList<HnComment>(commentElements.size());
        for (final Element e : commentElements) {
            final HnComment comment = parseComment(parents, e);
            if (comment != null && comment.getParent() == null) {
                comments.add(comment);
            }
        }
        return comments;
    }
}
