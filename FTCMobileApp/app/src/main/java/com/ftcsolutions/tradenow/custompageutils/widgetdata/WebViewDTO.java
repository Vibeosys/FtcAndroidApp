package com.ftcsolutions.tradenow.custompageutils.widgetdata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.ftcsolutions.tradenow.data.BaseDTO;

/**
 * Created by akshay on 30-06-2016.
 */
public class WebViewDTO extends BaseDTO {

    private static final String TAG = WebViewDTO.class.getSimpleName();
    private String view;

    public WebViewDTO() {
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public static WebViewDTO deserializeJson(String serializedString) {
        Gson gson = new Gson();
        WebViewDTO webViewDTO = null;
        try {
            webViewDTO = gson.fromJson(serializedString, WebViewDTO.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization WebViewDTO" + e.toString());
        }
        return webViewDTO;
    }
}
