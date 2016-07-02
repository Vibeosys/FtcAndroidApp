package com.vibeosys.tradenow.data.adapterdata;

/**
 * Created by akshay on 02-07-2016.
 */
public class NotificationsDTO {

    private int mNotificationId;
    private String mNotificationTitle;
    private String mNotificationDesc;
    private String mNotificationDate;
    private int mIsRead;

    public NotificationsDTO(int mNotificationId, String mNotificationTitle, String mNotificationDesc,
                            String mNotificationDate, int mIsRead) {
        this.mNotificationId = mNotificationId;
        this.mNotificationTitle = mNotificationTitle;
        this.mNotificationDesc = mNotificationDesc;
        this.mNotificationDate = mNotificationDate;
        this.mIsRead = mIsRead;
    }

    public int getmNotificationId() {
        return mNotificationId;
    }

    public void setmNotificationId(int mNotificationId) {
        this.mNotificationId = mNotificationId;
    }

    public String getmNotificationTitle() {
        return mNotificationTitle;
    }

    public void setmNotificationTitle(String mNotificationTitle) {
        this.mNotificationTitle = mNotificationTitle;
    }

    public String getmNotificationDesc() {
        return mNotificationDesc;
    }

    public void setmNotificationDesc(String mNotificationDesc) {
        this.mNotificationDesc = mNotificationDesc;
    }

    public String getmNotificationDate() {
        return mNotificationDate;
    }

    public void setmNotificationDate(String mNotificationDate) {
        this.mNotificationDate = mNotificationDate;
    }

    public int getmIsRead() {
        return mIsRead;
    }

    public void setmIsRead(int mIsRead) {
        this.mIsRead = mIsRead;
    }
}
