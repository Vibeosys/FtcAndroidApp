package com.vibeosys.tradenow.custompageutils.widgetdata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class TextDataDTO extends BaseDTO {

    private static final String TAG = TextDataDTO.class.getSimpleName();
    private String text;

    public TextDataDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static TextDataDTO deserializeJson(String serializedString) {
        Gson gson = new Gson();
        TextDataDTO textDataDTO = null;
        try {
            textDataDTO = gson.fromJson(serializedString, TextDataDTO.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization TextDataDTO" + e.toString());
        }
        return textDataDTO;
    }
}
