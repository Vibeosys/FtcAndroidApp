package com.ftcsolutions.tradenow.newutils.parser;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import com.ftcsolutions.tradenow.newutils.News;
import com.ftcsolutions.tradenow.newutils.interfaces.NewsReader;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 21-06-2016.
 */
public class NewsForexFactoryParser implements NewsReader {
    // Constants indicting XML element names that we're interested in

    private static final int TAG_GUID = 1;
    private static final int TAG_TITLE = 2;
    private static final int TAG_LINK = 3;
    private static final int TAG_PUB_DATE = 4;
    private static final int TAG_DESCRIPTION = 5;

    // We don't use XML namespaces
    private static final String ns = null;


    public List<News> parse(InputStream in) throws XmlPullParserException,
            IOException, ParseException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<News> readFeed(XmlPullParser parser) throws XmlPullParserException,
            IOException, ParseException {

        List<News> newses = new ArrayList<News>();

        // Search for <rss> tags. These wrap the beginning/end of an forexindustrynews document.
        parser.require(XmlPullParser.START_TAG, ns, "rss");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the <News> tag. This tag repeates inside of <rss> for each
            // article in the rss.
            Log.d("News Feed", "## rss" + name);
            if (name.equals("channel")) {
                newses = readChannel(parser);
            } else {
                skip(parser);
            }
        }
        return newses;
    }


    private List<News> readChannel(XmlPullParser parser) throws XmlPullParserException,
            IOException, ParseException {
        List<News> newses = new ArrayList<News>();

        // Search for <rss> tags. These wrap the beginning/end of an forexindustrynews document.
        parser.require(XmlPullParser.START_TAG, ns, "channel");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the <News> tag. This tag repeates inside of <rss> for each
            // article in the rss.
            Log.d("News Feed", "## channel " + name);
            if (name.equals("item")) {
                newses.add(readNews(parser));
            } else {
                skip(parser);
            }
        }
        return newses;
    }

    /**
     * Parses the contents of an news. If it encounters a title, summary, or link tag, hands them
     * off to their respective "read" methods for processing. Otherwise, skips the tag.
     */

    private News readNews(XmlPullParser parser) throws XmlPullParserException, IOException,
            ParseException {
        parser.require(XmlPullParser.START_TAG, ns, "item");

        String guid = null;
        String title = null;
        String link = null;
        String pubdate = null;
        String description = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            Log.d("News Feed", "## item " + name);
            if (name.equals("guid")) {
                guid = readTag(parser, TAG_GUID);
            } else if (name.equals("title")) {
                title = readTag(parser, TAG_TITLE);
            } else if (name.equals("link")) {
                link = readTag(parser, TAG_LINK);
            } else if (name.equals("pubDate")) {
                /*Time t = new Time();
                t.parse3339(readTag(parser, TAG_PUB_DATE));*/
                pubdate = readTag(parser, TAG_PUB_DATE);
            } else if (name.equals("description")) {
                description = readTag(parser, TAG_DESCRIPTION);
            } else {
                skip(parser);
            }
        }
        return new News(title, link, guid, pubdate, description);
    }

    /**
     * Process an incoming tag and read the selected value from it.
     */
    private String readTag(XmlPullParser parser, int tagType) throws XmlPullParserException,
            IOException {
        String tag = null;
        String endTag = null;
        switch (tagType) {
            case TAG_GUID:
                return readBasicTag(parser, "guid");
            case TAG_TITLE:
                return readBasicTag(parser, "title");
            case TAG_LINK:
                return readBasicTag(parser, "link");
            case TAG_DESCRIPTION:
                return readBasicTag(parser, "description");
            case TAG_PUB_DATE:
                return readBasicTag(parser, "pubDate");
            default:
                throw new IllegalArgumentException("Unknown Tag Type:" + tagType);
        }
    }

    /**
     * Reads the body of a basic XML tag, which is guaranteed not to contain any nested elements.
     */
    private String readBasicTag(XmlPullParser parser, String tag) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    /**
     * For the tags title and summary, extracts their text values.
     */

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = null;
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /**
     * Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
     * if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
     * finds the matching END_TAG (as indicated by the value of "depth" being 0).
     */
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
