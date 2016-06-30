package com.vibeosys.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.activities.CustomWebViewActivity;
import com.vibeosys.tradenow.activities.WebViewActivity;
import com.vibeosys.tradenow.custompageutils.widgetdata.LinkDataDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class LinkTextView extends TextView implements View.OnClickListener {

    private Paint mLinePaint;
    private Context mContext;
    private String mWidgetData;
    private LinkDataDTO linkDataDTO;

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
        final int paddingLeft = 3;
        final int paddingRight = 3;
        final int paddingTop = 3;
        final int paddingBottom = 3;
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, 8, 8, 8);
        setTextColor(mContext.getResources().getColor(R.color.hyperlink_color));
        linkDataDTO = LinkDataDTO.deserializeJson(mWidgetData);
        setText(linkDataDTO.getCaption());
        setLayoutParams(lp);
        setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        Intent iWebView = new Intent(getContext(), CustomWebViewActivity.class);
        iWebView.putExtra("link", linkDataDTO.getLink());
        iWebView.putExtra("title", linkDataDTO.getCaption());
        iWebView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(iWebView);
    }
}
