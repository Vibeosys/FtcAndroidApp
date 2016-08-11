package com.ftcsolutions.tradenow.data;

import java.util.ArrayList;

/**
 * Created by akshay on 30-06-2016.
 */
public class TableJsonCollectionDTO {

    private ArrayList<String> mInsertJsonList;
    private ArrayList<String> mUpdateJsonList;
    private ArrayList<String> mDeleteJsonList;

    public ArrayList<String> getInsertJsonList() {
        if (mInsertJsonList == null)
            mInsertJsonList = new ArrayList<>();

        return mInsertJsonList;
    }

    public ArrayList<String> getUpdateJsonList() {
        if (mUpdateJsonList == null)
            mUpdateJsonList = new ArrayList<>();

        return mUpdateJsonList;
    }

    public ArrayList<String> getDeleteJsonList() {
        if (mDeleteJsonList == null)
            mDeleteJsonList = new ArrayList<>();
        return mDeleteJsonList;
    }
}
