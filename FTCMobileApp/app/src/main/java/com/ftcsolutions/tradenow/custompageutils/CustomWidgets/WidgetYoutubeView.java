package com.ftcsolutions.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.activities.WidgetYouTubeActivity;
import com.ftcsolutions.tradenow.custompageutils.widgetdata.YoutubeDataDTO;

/**
 * Created by shrinivas on 13-07-2016.
 */
public class WidgetYoutubeView extends ImageView implements View.OnClickListener {
    private Context mContext;
    private String mWidgetData;
    private YoutubeDataDTO youtubeDataDTO;

    public WidgetYoutubeView(Context context) {
        super(context);
        init();
    }

    public WidgetYoutubeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WidgetYoutubeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public WidgetYoutubeView(Context context, String data) {
        super(context);
        this.mContext = context;
        this.mWidgetData = data;
        init();
    }

    public void init() {


        setPadding(2, 2, 2, 2);
        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, 16, 8, 16);
        setLayoutParams(lp);
        setImageDrawable(getResources().getDrawable(R.drawable.youtube_player_icon));

        youtubeDataDTO = YoutubeDataDTO.deserializeJson(mWidgetData);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent iWebView = new Intent(getContext(), WidgetYouTubeActivity.class);
        iWebView.putExtra("code", youtubeDataDTO.getCode());
        iWebView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(iWebView);
    }
}
