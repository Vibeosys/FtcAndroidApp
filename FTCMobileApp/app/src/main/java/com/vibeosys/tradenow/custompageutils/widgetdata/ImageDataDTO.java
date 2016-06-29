package com.vibeosys.tradenow.custompageutils.widgetdata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class ImageDataDTO extends BaseDTO {

    private static final String TAG = ImageDataDTO.class.getSimpleName();
    private String url;

    public ImageDataDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static ImageDataDTO deserializeJson(String serializedString) {
        Gson gson = new Gson();
        ImageDataDTO imageDataDTO = null;
        try {
            imageDataDTO = gson.fromJson(serializedString, ImageDataDTO.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization ImageDataDTO" + e.toString());
        }
        return imageDataDTO;
    }
}
