package com.ftcsolutions.tradenow.custompageutils.widgetdata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.ftcsolutions.tradenow.data.BaseDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class HeadingDataDTO extends BaseDTO {

    private static final String TAG = HeadingDataDTO.class.getSimpleName();
    private String head;

    public HeadingDataDTO() {
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public static HeadingDataDTO deserializeJson(String serializedString) {
        Gson gson = new Gson();
        HeadingDataDTO headingDataDTO = null;
        try {
            headingDataDTO = gson.fromJson(serializedString, HeadingDataDTO.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization HeadingDataDTO" + e.toString());
        }
        return headingDataDTO;
    }
}
