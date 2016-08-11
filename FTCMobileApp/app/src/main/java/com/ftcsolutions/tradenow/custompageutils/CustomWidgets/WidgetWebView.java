package com.ftcsolutions.tradenow.custompageutils.CustomWidgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ftcsolutions.tradenow.custompageutils.widgetdata.WebViewDTO;

/**
 * Created by akshay on 30-06-2016.
 */
public class WidgetWebView extends WebView {

    private Context mContext;
    private String mWidgetData;
    private View formView, progressView;

    public WidgetWebView(Context context, String widgetData, View formView, View progressView) {
        super(context);
        this.mContext = context;
        this.mWidgetData = widgetData;
        this.formView = formView;
        this.progressView = progressView;
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
        //init();
    }

    public WidgetWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //init();
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
        setWebViewClient(new WebClient());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private class WebClient extends WebViewClient {

        public WebClient() {
            showProgress(true, formView, progressView);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            showProgress(false, formView, progressView);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, final View hideFormView, final View showProgressView) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            if (hideFormView != null) {
                hideFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                hideFormView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });
            }
            if (showProgressView != null) {
                showProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                showProgressView.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        showProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            }
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            showProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            hideFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
