package com.vibeosys.tradenow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.utils.AppConstants;

public class WidgetYouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener {

    private static final String TAG = WidgetYouTubeActivity.class.getSimpleName();
    private ImageButton mClose;
    private String mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_you_tube);
        try {
            mCode = getIntent().getExtras().getString("code");
        } catch (Exception e) {
            Log.e(TAG, "Error to get the data");
        }
        mClose = (ImageButton) findViewById(R.id.videoClose);
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.YoutubePlayer);
        youTubeView.initialize(AppConstants.YOUTUBE_AUTH_KEY, this);
        mClose.setOnClickListener(this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        youTubePlayer.setFullscreen(false);
        youTubePlayer.setShowFullscreenButton(false);
        if (!wasRestored) {
            try {
                youTubePlayer.loadVideo(mCode);
            }catch (IllegalStateException ise)
            {
                return;
            }


        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onClick(View v) {
        int getItem = v.getId();
        switch (getItem) {
            case R.id.videoClose:
                finish();
                break;
        }

    }
}
