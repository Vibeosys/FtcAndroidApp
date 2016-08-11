package com.ftcsolutions.tradenow.custompageutils.CustomWidgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ftcsolutions.tradenow.activities.WebViewActivity;
import com.ftcsolutions.tradenow.adapters.NewsAdapter;
import com.ftcsolutions.tradenow.custompageutils.widgetdata.RssViewDTO;
import com.ftcsolutions.tradenow.newutils.News;
import com.ftcsolutions.tradenow.newutils.data.XMLObject;
import com.ftcsolutions.tradenow.newutils.parser.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 30-06-2016.
 */
public class WidgetRssFeed extends ListView implements AdapterView.OnItemClickListener {

    private Context mContext;
    private String mWidgetData;
    private List<News> newses = new ArrayList<>();
    private NewsAdapter adapter;
    private View formView, progressView;
    private RssViewDTO rssViewDTO;

    public WidgetRssFeed(Context context, String widgetData, View formView, View progressView) {
        super(context);
        this.mContext = context;
        this.mWidgetData = widgetData;
        this.formView = formView;
        this.progressView = progressView;
        init();
    }

    public WidgetRssFeed(Context context, String widgetData) {
        super(context);
        this.mContext = context;
        this.mWidgetData = widgetData;
        init();
    }

    public WidgetRssFeed(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        //init();
    }

    public WidgetRssFeed(Context context, AttributeSet attrs, int defStyleAttr) {
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
        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(8, 8, 8, 8);
        setLayoutParams(lp);
        setOnItemClickListener(this);
        rssViewDTO = RssViewDTO.deserializeJson(mWidgetData);
        AsyncNewsFetch asyncNewsFetch = new AsyncNewsFetch();
        asyncNewsFetch.execute();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News news = (News) adapter.getItem(position);
        Intent iDetails = new Intent(getContext(), WebViewActivity.class);
        iDetails.putExtra("link", news.getLink());
        iDetails.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(iDetails);
    }

    private class AsyncNewsFetch extends AsyncTask<Void, Void, XMLObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true, formView, progressView);
        }

        InputStream stream;
        XMLObject mObject = null;

        @Override
        protected XMLObject doInBackground(Void... params) {
            try {
                URL url = new URL(rssViewDTO.getFeed());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                // Starts the query
                conn.connect();
                stream = conn.getInputStream();

                /*NewsForexFactoryParser parser = new NewsForexFactoryParser();
                newses = parser.parse(stream);*/
               /* NewsFactory newsFactory = new NewsFactory();
                NewsReader newsReader = newsFactory.getNews(NewsStandConstants.FOREX_FACTORY_NEWS);
                newses = newsReader.parse(stream);*/
                try {
                    mObject = XmlParser.parseXml(stream);

                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(XMLObject aVoid) {
            super.onPostExecute(aVoid);
            Log.d("", "##" + mObject);
            if (mObject != null) {
                display(mObject);
            }

            Log.d("TAG", "## News" + newses);
            adapter = new NewsAdapter(newses, mContext);
            setAdapter(adapter);
            adapter.notifyDataSetChanged();
            showProgress(false, formView, progressView);
        }
    }

    public void display(XMLObject mObject) {
        if (mObject != null) {
            /*HashMap<String, String> atts = mObject.getParams();*/
            /*if (atts != null && atts.size() > 0) {
                for (String s : atts.keySet()) {
                    Log.d("##KEY:" + mObject.getName() + " :: Attribute: ", "##"+s + "=" + atts.get(s));
                }
            }*/
            //Log.d("##KEY:" + mObject.getName() + " :: Value=", "##" + mObject.getValue());
            if (mObject.getName().equals(rssViewDTO.getFeedParent())) {
                List<XMLObject> mObjectChilds = mObject.getChilds();
                String title = null, link = null, guid = null, pubdate = null, description = null;
                for (XMLObject child : mObjectChilds) {
                    if (child.getName().equals(rssViewDTO.getFeedTitle())) {
                        title = child.getValue();
                    }
                    if (child.getName().equals(rssViewDTO.getFeedLink())) {
                        link = child.getValue();
                    }
                   /* if (child.getName().equals("guid")) {
                        guid = child.getValue();
                    }*/
                    if (child.getName().equals(rssViewDTO.getFeedDate())) {
                        pubdate = child.getValue();
                    }
                    if (child.getName().equals(rssViewDTO.getFeedDescription())) {
                        description = child.getValue();
                    }
                }
                News news = new News(title, link, pubdate, description);
                newses.add(news);
            }
            List<XMLObject> mXmlObjects = mObject.getChilds();
            if (mXmlObjects != null && mXmlObjects.size() > 0) {
                for (XMLObject xmlObject : mXmlObjects) {
                    display(xmlObject);
                }
            }
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
