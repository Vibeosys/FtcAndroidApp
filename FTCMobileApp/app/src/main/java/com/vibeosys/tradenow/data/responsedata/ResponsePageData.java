package com.vibeosys.tradenow.data.responsedata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vibeosys.tradenow.data.BaseDTO;

import java.util.ArrayList;

/**
 * Created by akshay on 28-06-2016.
 */
public class ResponsePageData extends BaseDTO {

    private int pageId;
    private String pageTitle;
    private int status;
    private int pageType;
    private int active;

    public ResponsePageData() {
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPageType() {
        return pageType;
    }

    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public static ArrayList<ResponsePageData> deserializeToArray(String serializedString) {
        Gson gson = new Gson();
        ArrayList<ResponsePageData> pages = null;
        try {
            ResponsePageData[] deserializeObject = gson.fromJson(serializedString, ResponsePageData[].class);
            pages = new ArrayList<>();
            for (ResponsePageData mobilePage : deserializeObject) {
                pages.add(mobilePage);
            }
        } catch (JsonSyntaxException e) {
            Log.e("deserialize", "Response Page DTO error" + e.toString());
        }


        return pages;
    }
}
