package com.ftcsolutions.tradenow.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.utils.NetworkUtils;

public class CustomWebViewActivity extends BaseActivity {
    private WebView mWebView;
    private String mUrl;
    //private ProgressBar progressBar;
    View formView, progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_web_view);
        String title = getIntent().getExtras().getString("title");
        setTitle(title);
        try {

        } catch (NullPointerException e) {
            Log.d("TAG ", "Link not get");
        }
        mUrl = getIntent().getExtras().getString("link");
        mWebView = (WebView) findViewById(R.id.addDetails);
        progressBar = findViewById(R.id.progressBar);
        formView = findViewById(R.id.formView);
        if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext())) {
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(mUrl);
            mWebView.setWebViewClient(new NewsDetails());
        } else {

        }

    }

    @Override
    protected View getMainView() throws NullPointerException {
        return formView;
    }

    private class NewsDetails extends WebViewClient {

        public NewsDetails() {
            showProgress(true, formView, progressBar);
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
            showProgress(false, formView, progressBar);
        }
    }
}
