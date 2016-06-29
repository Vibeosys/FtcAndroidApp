package com.vibeosys.tradenow.custompageutils.widgetdata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class VideoDataDTO extends BaseDTO {

    private static final String TAG = VideoDataDTO.class.getSimpleName();
    private String url;

    public VideoDataDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static VideoDataDTO deserializeJson(String serializedString) {
        Gson gson = new Gson();
        VideoDataDTO videoDataDTO = null;
        try {
            videoDataDTO = gson.fromJson(serializedString, VideoDataDTO.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization VideoDataDTO" + e.toString());
        }
        return videoDataDTO;
    }
}
