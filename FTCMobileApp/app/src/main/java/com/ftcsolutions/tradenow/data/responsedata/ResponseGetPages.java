package com.ftcsolutions.tradenow.data.responsedata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.ftcsolutions.tradenow.data.BaseDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class ResponseGetPages extends BaseDTO {

    private static final String TAG = ResponseGetPages.class.getSimpleName();
    private String pageType;
    private String pages;
    private String widgets;

    public ResponseGetPages() {
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getWidgets() {
        return widgets;
    }

    public void setWidgets(String widgets) {
        this.widgets = widgets;
    }

    public static ResponseGetPages deserializeJson(String serializedString) {
        Gson gson = new Gson();
        ResponseGetPages getPages = null;
        try {
            getPages = gson.fromJson(serializedString, ResponseGetPages.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization" + e.toString());
        }
        return getPages;
    }
}
