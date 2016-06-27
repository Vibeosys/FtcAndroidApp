package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.NewsAdapter;
import com.vibeosys.tradenow.newutils.News;
import com.vibeosys.tradenow.newutils.NewsFactory;
import com.vibeosys.tradenow.newutils.NewsStandConstants;
import com.vibeosys.tradenow.newutils.data.XMLObject;
import com.vibeosys.tradenow.newutils.interfaces.NewsReader;
import com.vibeosys.tradenow.newutils.parser.NewsForexFactoryParser;
import com.vibeosys.tradenow.newutils.parser.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsActivity extends BaseActivity {

    List<News> newses = new ArrayList<>();
    private View formView;
    private View progressBar;
    NewsAdapter adapter;
    ListView listNews;
    View mainNewsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle(getResources().getString(R.string.news));
        formView = findViewById(R.id.formView);
        progressBar = findViewById(R.id.progressBar);
        listNews = (ListView) findViewById(R.id.listNews);
        mainNewsView = findViewById(R.id.mainNewsView);
        AsyncNewsFetch fetch = new AsyncNewsFetch();
        fetch.execute();

        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) adapter.getItem(position);
                Intent iDetails = new Intent(getApplicationContext(), NewsDetailsActivity.class);
                iDetails.putExtra("link", news.getLink());
                startActivity(iDetails);
            }
        });

    }

    @Override
    protected View getMainView() {
        return this.mainNewsView;
    }

    private class AsyncNewsFetch extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true, formView, progressBar);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http://rss.forexfactory.net/news/forexindustrynews.xml");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                // Starts the query
                conn.connect();
                InputStream stream = conn.getInputStream();

                /*NewsForexFactoryParser parser = new NewsForexFactoryParser();
                newses = parser.parse(stream);*/
               /* NewsFactory newsFactory = new NewsFactory();
                NewsReader newsReader = newsFactory.getNews(NewsStandConstants.FOREX_FACTORY_NEWS);
                newses = newsReader.parse(stream);*/
                XMLObject mObject = XmlParser.parseXml(stream);
                if (mObject != null) {
                    display(mObject);
                }

                stream.close();
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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("TAG", "## News" + newses);
            adapter = new NewsAdapter(newses, getApplicationContext());
            listNews.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            showProgress(false, formView, progressBar);

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
