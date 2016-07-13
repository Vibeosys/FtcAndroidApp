package com.vibeosys.tradenow.custompageutils.widgetdata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.vibeosys.tradenow.data.BaseDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shrinivas on 13-07-2016.
 */
public class YoutubeDataDTO extends BaseDTO {

    private static final String TAG = YoutubeDataDTO.class.getSimpleName();
    private String link;


    public YoutubeDataDTO() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCode() {
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(this.link); //url is youtube url for which you want to extract the id.
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static YoutubeDataDTO deserializeJson(String serializedString) {
        Gson gson = new Gson();
        YoutubeDataDTO youtubeDataDTO = null;
        try {
            youtubeDataDTO = gson.fromJson(serializedString, YoutubeDataDTO.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization VideoDataDTO" + e.toString());
        }
        return youtubeDataDTO;
    }
}
