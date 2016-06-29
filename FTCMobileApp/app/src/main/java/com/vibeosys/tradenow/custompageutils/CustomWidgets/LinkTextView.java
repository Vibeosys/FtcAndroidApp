package com.vibeosys.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.custompageutils.widgetdata.LinkDataDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class LinkTextView extends TextView {

    private Paint mLinePaint;
    private Context mContext;
    private String mWidgetData;

    public LinkTextView(Context context, String widgetData) {
        super(context);
        mContext = context;
        this.mWidgetData = widgetData;
        init();
    }


    public LinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public LinkTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mContext.getResources().getColor(R.color.hyperlink_color));
        final int paddingLeft = 2;
        final int paddingRight = 2;
        final int paddingTop = 2;
        final int paddingBottom = 2;
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        setTextColor(mContext.getResources().getColor(R.color.hyperlink_color));
        LinkDataDTO linkDataDTO = LinkDataDTO.deserializeJson(mWidgetData);
        setText(linkDataDTO.getCaption());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), mLinePaint);
        canvas.save();
        super.onDraw(canvas);
        canvas.restore();
    }

    public String getWidgetData() {
        return mWidgetData;
    }

    public void setWidgetData(String mWidgetData) {
        this.mWidgetData = mWidgetData;
    }
}
