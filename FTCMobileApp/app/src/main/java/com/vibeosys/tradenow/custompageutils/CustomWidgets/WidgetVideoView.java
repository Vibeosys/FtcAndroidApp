package com.vibeosys.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.vibeosys.tradenow.custompageutils.widgetdata.VideoDataDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class WidgetVideoView extends VideoView {

    private Context mContext;
    private String mWidgetData;

    public WidgetVideoView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }


    public WidgetVideoView(Context context, String widgetData) {
        super(context);
        this.mContext = context;
        this.mWidgetData = widgetData;
        init();
    }

    public WidgetVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public WidgetVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        final int paddingLeft = 2;
        final int paddingRight = 2;
        final int paddingTop = 2;
        final int paddingBottom = 2;
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        VideoDataDTO videoDataDTO = VideoDataDTO.deserializeJson(mWidgetData);
        Uri vidUri = Uri.parse(videoDataDTO.getUrl());
        setVideoURI(vidUri);
        MediaController vidControl = new MediaController(mContext);
        vidControl.setAnchorView(this);
        setMediaController(vidControl);
        start();
        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, 8, 8, 8);
        setLayoutParams(lp);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
