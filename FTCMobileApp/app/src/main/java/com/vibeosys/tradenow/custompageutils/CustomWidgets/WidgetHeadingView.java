package com.vibeosys.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.custompageutils.widgetdata.HeadingDataDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class WidgetHeadingView extends TextView {

    private Context mContext;
    private String mWidgetData;

    public WidgetHeadingView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public WidgetHeadingView(Context context, String widgetData) {
        super(context);
        this.mContext = context;
        this.mWidgetData = widgetData;
        init();
    }

    public WidgetHeadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public WidgetHeadingView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        setTextColor(getResources().getColor(R.color.primaryText));
        setTextSize(20);
        HeadingDataDTO headingDataDTO = HeadingDataDTO.deserializeJson(mWidgetData);
        setText(headingDataDTO.getHead());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
