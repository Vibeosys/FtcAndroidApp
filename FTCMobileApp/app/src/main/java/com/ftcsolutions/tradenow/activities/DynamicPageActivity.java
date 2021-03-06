package com.ftcsolutions.tradenow.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.custompageutils.WidgetTypes;
import com.ftcsolutions.tradenow.custompageutils.pagedata.PageWidgetDTO;

import java.util.ArrayList;

public class DynamicPageActivity extends BaseActivity {

    private static final String TAG = DynamicPageActivity.class.getSimpleName();

    private final int PAGE_TYPE_CUSTOM = 1;
    private final int PAGE_TYPE_WEB_VIEW = 2;
    private final int PAGE_TYPE_RSS = 3;

    private String pageTitle;
    private LinearLayout mLinearLayout;
    private View progressBar;
    private TextView mTxtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTitle = getIntent().getExtras().getString("pageTitle");
        setTitle(pageTitle);

        if (!TextUtils.isEmpty(pageTitle) || pageTitle != null) {
            int pageTypeId = mDbRepository.getPageType(pageTitle);
            if (pageTypeId == PAGE_TYPE_RSS) {
                setContentView(R.layout.widget_rss_feed_dynamic_page);
            } else {
                setContentView(R.layout.activity_dynamic_page);
            }
            mLinearLayout = (LinearLayout) findViewById(R.id.mainView);
            progressBar = findViewById(R.id.progressBar);
            mTxtError = (TextView) findViewById(R.id.txtError);
            SetUpUi setUpUi = new SetUpUi();
            setUpUi.execute(pageTitle);
        } else {
            mTxtError.setVisibility(View.VISIBLE);
            mLinearLayout.setVisibility(View.GONE);
        }
    }


    private class SetUpUi extends AsyncTask<String, Void, Void> {
        ArrayList<PageWidgetDTO> widgetDTOs = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true, mLinearLayout, progressBar);
        }

        @Override
        protected Void doInBackground(String... params) {
            widgetDTOs = mDbRepository.getWidgets(params[0]);
            Log.d(TAG, "##" + widgetDTOs.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showProgress(false, mLinearLayout, progressBar);
            for (int i = 0; i < widgetDTOs.size(); i++) {
                PageWidgetDTO pageWidgetDTO = widgetDTOs.get(i);
                WidgetTypes widgetTypes = new WidgetTypes(pageWidgetDTO, DynamicPageActivity.this,
                        mLinearLayout, progressBar);
                try {
                    mLinearLayout.addView(widgetTypes.getView());
                } catch (NullPointerException e) {
                    mTxtError.setVisibility(View.VISIBLE);
                    mLinearLayout.setVisibility(View.GONE);
                    mTxtError.setText(getResources().getString(R.string.str_err_server_msg));
                } catch (IllegalArgumentException e) {
                    mTxtError.setVisibility(View.VISIBLE);
                    mLinearLayout.setVisibility(View.GONE);
                    mTxtError.setText(getResources().getString(R.string.str_err_server_msg));
                }

            }

        }
    }

    @Override
    protected View getMainView() throws NullPointerException {
        return mLinearLayout;
    }
}
