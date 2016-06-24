package com.vibeosys.tradenow.newutils.interfaces;

import com.vibeosys.tradenow.newutils.News;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

/**
 * Callback for handling async task states
 */
public interface NewsReader {
    List<News> parse(InputStream in) throws XmlPullParserException,
            IOException, ParseException;
}
