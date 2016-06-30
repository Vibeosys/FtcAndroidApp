package com.vibeosys.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.custompageutils.widgetdata.ImageDataDTO;
import com.vibeosys.tradenow.utils.CustomVolleyRequestQueue;

/**
 * Created by akshay on 29-06-2016.
 */
public class WidgetImageView extends NetworkImageView {

    private Context mContext;
    private String mWidgetData;
    private ImageLoader mImageLoader;

    public WidgetImageView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public WidgetImageView(Context context, String widgetData) {
        super(context);
        this.mContext = context;
        this.mWidgetData = widgetData;
        init();
    }

    public WidgetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public WidgetImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        ImageDataDTO imageDataDTO = ImageDataDTO.deserializeJson(this.mWidgetData);
        String url = imageDataDTO.getUrl();
        mImageLoader = CustomVolleyRequestQueue.getInstance(mContext)
                .getImageLoader();
        if (url != null && !url.isEmpty()) {
            try {
                mImageLoader.get(url, ImageLoader.getImageListener(this,
                        R.drawable.ic_icon, R.drawable.ic_icon));
                setImageUrl(url, mImageLoader);
            } catch (Exception e) {
                setImageResource(R.drawable.ic_icon);
            }
        } else {
            setImageResource(R.drawable.ic_icon);
        }
        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(8, 8, 8, 8);
        setLayoutParams(lp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
