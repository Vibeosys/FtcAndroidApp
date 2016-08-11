package com.ftcsolutions.tradenow.custompageutils.pagedata;

/**
 * Created by akshay on 29-06-2016.
 */
public class PageWidgetDTO {

    private int mWidgetId;
    private String mWidgetTitle;
    private int mPosition;
    private String mWidgetData;

    public PageWidgetDTO(int mWidgetId, String mWidgetTitle, int mPosition, String mWidgetData) {
        this.mWidgetId = mWidgetId;
        this.mWidgetTitle = mWidgetTitle;
        this.mPosition = mPosition;
        this.mWidgetData = mWidgetData;
    }

    public int getWidgetId() {
        return mWidgetId;
    }

    public void setWidgetId(int mWidgetId) {
        this.mWidgetId = mWidgetId;
    }

    public String getWidgetTitle() {
        return mWidgetTitle;
    }

    public void setWidgetTitle(String mWidgetTitle) {
        this.mWidgetTitle = mWidgetTitle;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public String getWidgetData() {
        return mWidgetData;
    }

    public void setWidgetData(String mWidgetData) {
        this.mWidgetData = mWidgetData;
    }

    @Override
    public String toString() {
        return "PageWidgetDTO{" +
                "mWidgetId=" + mWidgetId +
                ", mWidgetTitle='" + mWidgetTitle + '\'' +
                ", mPosition=" + mPosition +
                ", mWidgetData='" + mWidgetData + '\'' +
                '}';
    }
}
