package com.vibeosys.tradenow.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.custompageutils.WidgetTypes;
import com.vibeosys.tradenow.custompageutils.pagedata.PageWidgetDTO;

import java.util.ArrayList;

public class DynamicPageActivity extends BaseActivity {

    private static final String TAG = DynamicPageActivity.class.getSimpleName();
    private String pageTitle;
    private LinearLayout mLinearLayout;
    private View progressBar;
    private TextView mTxtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_page);
        pageTitle = getIntent().getExtras().getString("pageTitle");
        setTitle(pageTitle);
        mLinearLayout = (LinearLayout) findViewById(R.id.mainView);
        progressBar = findViewById(R.id.progressBar);
        mTxtError = (TextView) findViewById(R.id.txtError);
        if (!TextUtils.isEmpty(pageTitle) || pageTitle != null) {
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
                WidgetTypes widgetTypes = new WidgetTypes(pageWidgetDTO, getApplicationContext());
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
        return null;
    }
}
