package com.vibeosys.tradenow.data.responsedata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vibeosys.tradenow.data.BaseDTO;

import java.util.ArrayList;

/**
 * Created by akshay on 28-06-2016.
 */
public class ResponseWidgetData extends BaseDTO {

    private int widgetId;
    private String widgetTitle;
    private int position;
    private String data;
    private int pageId;

    public ResponseWidgetData() {
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public String getWidgetTitle() {
        return widgetTitle;
    }

    public void setWidgetTitle(String widgetTitle) {
        this.widgetTitle = widgetTitle;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public static ArrayList<ResponseWidgetData> deserializeToArray(String serializedString) {
        Gson gson = new Gson();
        ArrayList<ResponseWidgetData> widgetDatas = null;
        try {
            ResponseWidgetData[] deserializeObject = gson.fromJson(serializedString, ResponseWidgetData[].class);
            widgetDatas = new ArrayList<>();
            for (ResponseWidgetData widgetData : deserializeObject) {
                widgetDatas.add(widgetData);
            }
        } catch (JsonSyntaxException e) {
            Log.e("deserialize", "Response widget DTO error" + e.toString());
        }


        return widgetDatas;
    }
}
