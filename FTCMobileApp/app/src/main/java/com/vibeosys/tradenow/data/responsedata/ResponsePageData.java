package com.vibeosys.tradenow.data.responsedata;

/**
 * Created by akshay on 28-06-2016.
 */
public class ResponsePageData {

    private int pageId;
    private String pageTitle;
    private String status;
    private int pageTypeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPageTypeId() {
        return pageTypeId;
    }

    public void setPageTypeId(int pageTypeId) {
        this.pageTypeId = pageTypeId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
