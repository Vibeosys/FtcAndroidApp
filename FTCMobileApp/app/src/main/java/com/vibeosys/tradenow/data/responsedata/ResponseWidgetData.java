package com.vibeosys.tradenow.data.responsedata;

/**
 * Created by akshay on 28-06-2016.
 */
public class ResponseWidgetData {

    private int widgetId;
    private String widgetTitle;
    private int position;
    private String widgetData;
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
        return widgetData;
    }

    public void setWidgetData(String widgetData) {
        this.widgetData = widgetData;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
