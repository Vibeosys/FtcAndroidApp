package com.vibeosys.tradenow.custompageutils.CustomWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.NewsAdapter;
import com.vibeosys.tradenow.custompageutils.widgetdata.TextDataDTO;
import com.vibeosys.tradenow.newutils.News;
import com.vibeosys.tradenow.newutils.data.XMLObject;
import com.vibeosys.tradenow.newutils.parser.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 30-06-2016.
 */
public class WidgetRssFeed extends ListView {

    private Context mContext;
    private String mWidgetData;
    private List<News> newses = new ArrayList<>();
    private NewsAdapter adapter;

    public WidgetRssFeed(Context context) {
        super(context);
        this.mContext = context;
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
        init();
    }

    public WidgetRssFeed(Context context, AttributeSet attrs, int defStyleAttr) {
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
        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(8, 8, 8, 8);
        setLayoutParams(lp);


        AsyncNewsFetch asyncNewsFetch = new AsyncNewsFetch();
        asyncNewsFetch.execute();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private class AsyncNewsFetch extends AsyncTask<Void, Void, XMLObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        InputStream stream;
        XMLObject mObject = null;

        @Override
        protected XMLObject doInBackground(Void... params) {
            try {
                URL url = new URL("http://rss.forexfactory.net/news/forexindustrynews.xml");
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
            if (mObject.getName().equals("item")) {
                List<XMLObject> mObjectChilds = mObject.getChilds();
                String title = null, link = null, guid = null, pubdate = null, description = null;
                for (XMLObject child : mObjectChilds) {
                    if (child.getName().equals("title")) {
                        title = child.getValue();
                    }
                    if (child.getName().equals("link")) {
                        link = child.getValue();
                    }
                    if (child.getName().equals("guid")) {
                        guid = child.getValue();
                    }
                    if (child.getName().equals("pubDate")) {
                        pubdate = child.getValue();
                    }
                    if (child.getName().equals("description")) {
                        description = child.getValue();
                    }
                }
                News news = new News(title, link, guid, pubdate, description);
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
}
