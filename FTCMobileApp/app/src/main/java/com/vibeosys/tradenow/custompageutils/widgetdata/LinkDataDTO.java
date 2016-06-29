package com.vibeosys.tradenow.custompageutils.widgetdata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class LinkDataDTO extends BaseDTO {

    private static final String TAG = LinkDataDTO.class.getSimpleName();
    private String link;
    private String caption;

    public LinkDataDTO() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public static LinkDataDTO deserializeJson(String serializedString) {
        Gson gson = new Gson();
        LinkDataDTO linkDataDTO = null;
        try {
            linkDataDTO = gson.fromJson(serializedString, LinkDataDTO.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization LinkDataDTO" + e.toString());
        }
        return linkDataDTO;
    }
}
