package com.vibeosys.tradenow.data.responsedata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vibeosys.tradenow.data.BaseDTO;

import java.util.ArrayList;

/**
 * Created by akshay on 29-06-2016.
 */
public class ResponsePageType extends BaseDTO {

    private int pageTypeId;
    private String pageTypeDesc;
    private int active;

    public int getPageTypeId() {
        return pageTypeId;
    }

    public void setPageTypeId(int pageTypeId) {
        this.pageTypeId = pageTypeId;
    }

    public String getPageTypeDesc() {
        return pageTypeDesc;
    }

    public void setPageTypeDesc(String pageTypeDesc) {
        this.pageTypeDesc = pageTypeDesc;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public static ArrayList<ResponsePageType> deserializeToArray(String serializedString) {
        Gson gson = new Gson();
        ArrayList<ResponsePageType> pageTypes = null;
        try {
            ResponsePageType[] deserializeObject = gson.fromJson(serializedString, ResponsePageType[].class);
            pageTypes = new ArrayList<>();
            for (ResponsePageType pageType : deserializeObject) {
                pageTypes.add(pageType);
            }
        } catch (JsonSyntaxException e) {
            Log.e("deserialize", "Response Page type DTO error" + e.toString());
        }


        return pageTypes;
    }
}
