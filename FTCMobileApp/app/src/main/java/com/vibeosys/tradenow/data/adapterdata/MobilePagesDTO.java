package com.vibeosys.tradenow.data.adapterdata;

/**
 * Created by akshay on 29-06-2016.
 */
public class MobilePagesDTO {

    private int mPageId;
    private String mPageTitle;

    public MobilePagesDTO(int mPageId, String mPageTitle) {
        this.mPageId = mPageId;
        this.mPageTitle = mPageTitle;
    }

    public int getmPageId() {
        return mPageId;
    }

    public void setmPageId(int mPageId) {
        this.mPageId = mPageId;
    }

    public String getmPageTitle() {
        return mPageTitle;
    }

    public void setmPageTitle(String mPageTitle) {
        this.mPageTitle = mPageTitle;
    }
}
