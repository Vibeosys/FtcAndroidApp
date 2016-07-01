package com.vibeosys.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.activities.DynamicPageActivity;
import com.vibeosys.tradenow.custompageutils.widgetdata.VideoDataDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class WidgetVideoView extends LinearLayout {

    private Context mContext;
    private String mWidgetData;
    private VideoView videoView;
    private LinearLayout linearLayout;

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
        inflate(mContext, R.layout.widget_video_view_layout, this);
        setPadding(0, 2, 0, 2);
        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 400);
        lp.setMargins(0, 16, 0, 16);
        setLayoutParams(lp);

        VideoView videoView = (VideoView) findViewById(R.id.widgetVideoView);
        VideoDataDTO videoDataDTO = VideoDataDTO.deserializeJson(mWidgetData);
        Uri vidUri = Uri.parse(videoDataDTO.getUrl());
        videoView.setVideoURI(vidUri);

        MediaController vidControl = new MediaController(mContext);
        vidControl.setAnchorView(this);
        videoView.setMediaController(vidControl);
        //start();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
