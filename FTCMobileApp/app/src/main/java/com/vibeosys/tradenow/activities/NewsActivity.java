package com.vibeosys.tradenow.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.NewsAdapter;
import com.vibeosys.tradenow.newutils.News;
import com.vibeosys.tradenow.newutils.NewsFactory;
import com.vibeosys.tradenow.newutils.NewsStandConstants;
import com.vibeosys.tradenow.newutils.interfaces.NewsReader;
import com.vibeosys.tradenow.newutils.parser.NewsForexFactoryParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
                NewsFactory newsFactory = new NewsFactory();
                NewsReader newsReader = newsFactory.getNews(NewsStandConstants.FOREX_FACTORY_NEWS);
                newses = newsReader.parse(stream);
                stream.close();
            } catch (Exception e) {
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
}
