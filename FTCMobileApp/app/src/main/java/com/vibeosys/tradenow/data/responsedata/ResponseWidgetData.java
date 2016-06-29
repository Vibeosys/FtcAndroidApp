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
    private int active;

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

    public String getWidgetData() {
        return data;
    }

    public void setWidgetData(String widgetData) {
        this.data = widgetData;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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
