package com.detwiler.hackernews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TopPostsSimpleTest {
    private Document mDocument;

    @Before
    public void setup() throws IOException {
        final InputStream input = getClass().getResourceAsStream("/html/top.html");
        mDocument = Jsoup.parse(input, "utf-8", "news.ycombinator.com");
    }

    /**
     * Test high-level parsing succeeded. This test just reads a document and ensures that the correct number of posts
     * were read.
     */
    @Test
    public void testParseSanity() {
        final HnConnection connection = mock(HnConnection.class);
        final HnPostListDocument postList = new HnPostListDocument(connection, mDocument, HnPostCategory.TOP);

        assertEquals(true, postList.hasMore());
        assertEquals(postList.getPosts().size(), 30);
    }

    /**
     * Verify correct parsing of the "more" link.
     */
    @Test
    public void testHasMore() {
        final HnConnection connection = mock(HnConnection.class);
        final HnPostListDocument postList = new HnPostListDocument(connection, mDocument, HnPostCategory.TOP);

        assertEquals(true, postList.hasMore());
    }

    /**
     * Verify all usernames are correctly parsed.
     */
    @Test
    public void testParseUsername() {
        final HnConnection connection = mock(HnConnection.class);
        final HnPostListDocument postList = new HnPostListDocument(connection, mDocument, HnPostCategory.TOP);
        final List<HnSubmission> submissions = postList.getPosts();
        final String[] expectedUsernames = {
                "jfb", "oBeLx", "hamdal", "jmsduran", "brchen", "ericthegoodking", "hk__2", "spking", "arikrak",
                "nsomaru", "1337biz", "samsolomon", "jonkratz", "yiedyie", "ulam2", "keeshawn", "ggreer", "Le_SDT",
                "coconutrandom", "ZanyProgrammer", "DanielRibeiro", "Vik1ng", "biscarch", "kqr2", "reirob", "bhaumik",
                "thealphanerd", "1ris", "zekers", "whbk"
        };
        int i = 0;
        final String[] actualUsernames = new String[30];
        for (final HnSubmission submission : submissions) {
            actualUsernames[i++] = submission.getUsername();
        }
        assertArrayEquals(expectedUsernames, actualUsernames);
    }

    /**
     * Verify all point values are correctly parsed.
     */
    @Test
    public void testParsePoints() {
        final HnConnection connection = mock(HnConnection.class);
        final HnPostListDocument postList = new HnPostListDocument(connection, mDocument, HnPostCategory.TOP);
        final List<HnSubmission> submissions = postList.getPosts();
        final int[] expectedPoints = {
                69, 42, 70, 23, 43, 17, 8, 27, 16, 88,
                44, 4, 187, 7, 34, 170, 157, 53, 225, 11,
                70, 325, 132, 124, 187, 386, 66, 585, 118, 61
        };
        int i = 0;
        final int[] actualPoints = new int[30];
        for (final HnSubmission submission : submissions) {
            actualPoints[i++] = submission.getPoints();
        }
        assertArrayEquals(expectedPoints, actualPoints);
    }
}
