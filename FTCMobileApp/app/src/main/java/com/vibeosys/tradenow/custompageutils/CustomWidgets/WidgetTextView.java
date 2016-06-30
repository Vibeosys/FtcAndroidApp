package com.vibeosys.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.custompageutils.widgetdata.HeadingDataDTO;
import com.vibeosys.tradenow.custompageutils.widgetdata.TextDataDTO;

/**
 * Created by akshay on 29-06-2016.
 */
public class WidgetTextView extends TextView {

    private Context mContext;
    private String mWidgetData;

    public WidgetTextView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public WidgetTextView(Context context, String widgetData) {
        super(context);
        this.mContext = context;
        this.mWidgetData = widgetData;
        init();
    }

    public WidgetTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public WidgetTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        setTextColor(getResources().getColor(R.color.secondaryText));
        setTextSize(16);
        TextDataDTO textDataDTO = TextDataDTO.deserializeJson(mWidgetData);
        setText(textDataDTO.getText());
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
