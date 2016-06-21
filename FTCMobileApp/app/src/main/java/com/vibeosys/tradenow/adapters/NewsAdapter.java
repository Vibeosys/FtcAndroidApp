package com.vibeosys.tradenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.shuttercomponent.ShutterHeader;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.xmlparser.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 14-06-2016.
 */
public class NewsAdapter extends BaseAdapter {

    private List<News> data;
    private Context mContext;
    ViewDetailsListener viewDetailsListener;

    public NewsAdapter(List<News> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder = null;
        if (row == null) {

            LayoutInflater theLayoutInflator = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = theLayoutInflator.inflate(R.layout.row_news, null);
            viewHolder = new ViewHolder();
            viewHolder.txtHeader = (TextView) row.findViewById(R.id.newsHeader);
            viewHolder.txtDesc = (TextView) row.findViewById(R.id.newsDesc);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        News news = data.get(position);
        viewHolder.txtHeader.setText(news.getTitle());
        viewHolder.txtDesc.setText(news.getDescription());
        return row;
    }

    private class ViewHolder {
        TextView txtHeader, txtDesc;
    }

    public void addItem(final News item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }

    public void setCustomButtonListner(ViewDetailsListener listener) {
        this.viewDetailsListener = listener;
    }

    public interface ViewDetailsListener {
        public void onViewClickListener(int id, int value, Object object);
    }
}
