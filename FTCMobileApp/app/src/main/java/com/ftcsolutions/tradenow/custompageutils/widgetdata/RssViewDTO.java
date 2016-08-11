package com.ftcsolutions.tradenow.custompageutils.widgetdata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.ftcsolutions.tradenow.data.BaseDTO;

/**
 * Created by akshay on 01-07-2016.
 */
public class RssViewDTO extends BaseDTO {

    private static final String TAG = RssViewDTO.class.getSimpleName();
    private String feed;
    private String feedParent;
    private String feedTitle;
    private String feedLink;
    private String feedDate;
    private String feedDescription;

    public RssViewDTO() {
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getFeedParent() {
        return feedParent;
    }

    public void setFeedParent(String feedParent) {
        this.feedParent = feedParent;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedLink() {
        return feedLink;
    }

    public void setFeedLink(String feedLink) {
        this.feedLink = feedLink;
    }

    public String getFeedDate() {
        return feedDate;
    }

    public void setFeedDate(String feedDate) {
        this.feedDate = feedDate;
    }

    public String getFeedDescription() {
        return feedDescription;
    }

    public void setFeedDescription(String feedDescription) {
        this.feedDescription = feedDescription;
    }

    public static RssViewDTO deserializeJson(String serializedString) {
        Gson gson = new Gson();
        RssViewDTO rssViewDTO = null;
        try {
            rssViewDTO = gson.fromJson(serializedString, RssViewDTO.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization RssViewDTO" + e.toString());
        }
        return rssViewDTO;
    }
}
