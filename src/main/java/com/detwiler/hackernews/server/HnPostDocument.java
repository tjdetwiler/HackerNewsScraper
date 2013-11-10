package com.detwiler.hackernews.server;

import com.detwiler.hackernews.HnComment;

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

    public HnPostDocument(final Document document) {
        super(document);
        mComments = parsePost();
    }

    public List<HnComment> getComments() {
        return Collections.unmodifiableList(mComments);
    }

    private HnComment parseComment(final Map<Integer, HnComment> parents,
                                   final Element commentElement) {
        Elements es = commentElement.select("td>img[src=s.gif]");
        Element e = es.get(0);
        HnComment comment = new HnComment();

        int depth = Integer.parseInt(e.attr("width")) / REPLY_INDENT;
        if (depth > 0) {
            HnComment parent = parents.get(depth-1);
            parent.addReply(comment);
            comment.setParent(parent);
        }
        parents.put(depth, comment);

        // Extract user/post id
        es = commentElement.select("span.comhead>a");
        System.out.println(">>>>" + es.get(0));
        String userId = es.get(0).text();
        String commentHref = es.get(1).attr("href");
        String postId = "";
        Matcher m = SELF_POST_REGEX.matcher(commentHref);
        if (m.matches())
            postId = m.group(1);
        String text = commentElement.select("span.comment>font").get(0).text();

        comment.setUsername(userId);
        comment.setPostId(postId);
        comment.setText(text);
        return comment;
    }

    private List<HnComment> parsePost() {
        // so. many. tables.
        Elements commentElements = getDocument().select("table table table");
        Map<Integer, HnComment> parents = new HashMap<Integer, HnComment>();
        List<HnComment> comments = new ArrayList<HnComment>(commentElements.size());
        for (Element e : commentElements) {
            comments.add(parseComment(parents, e));
        }
        return comments;
    }
}
