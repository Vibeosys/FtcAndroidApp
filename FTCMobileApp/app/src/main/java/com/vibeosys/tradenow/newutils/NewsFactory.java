package com.vibeosys.tradenow.newutils;

import com.vibeosys.tradenow.newutils.interfaces.NewsReader;
import com.vibeosys.tradenow.newutils.parser.NewsForexFactoryParser;

/**
 * Created by akshay on 24-06-2016.
 */
public class NewsFactory {

    public NewsReader getNews(String newsStand) {
        if (newsStand == null) {
            return null;
        }
        if (newsStand.equalsIgnoreCase(NewsStandConstants.FOREX_FACTORY_NEWS)) {
            return new NewsForexFactoryParser();
        }

        return null;
    }
}
