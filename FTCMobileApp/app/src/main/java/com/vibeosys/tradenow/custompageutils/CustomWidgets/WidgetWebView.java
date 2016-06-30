package com.vibeosys.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vibeosys.tradenow.custompageutils.widgetdata.WebViewDTO;

/**
 * Created by akshay on 30-06-2016.
 */
public class WidgetWebView extends WebView {

    private Context mContext;
    private String mWidgetData;

    public WidgetWebView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public WidgetWebView(Context context, String widgetData) {
        super(context);
        this.mContext = context;
        this.mWidgetData = widgetData;
        init();
    }

    public WidgetWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public WidgetWebView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        WebViewDTO webViewDTO = WebViewDTO.deserializeJson(mWidgetData);
        loadUrl(webViewDTO.getView());
        getSettings().setJavaScriptEnabled(true);
        //setWebViewClient(new WebClient());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
