package com.vibeosys.tradenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.tradenow.R;

import java.util.ArrayList;

/**
 * Created by akshay on 14-06-2016.
 */
public class NotificationAdapter extends BaseAdapter {

    private ArrayList<Integer> data;
    private Context mContext;

    public NotificationAdapter(ArrayList<Integer> data, Context mContext) {
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
            row = theLayoutInflator.inflate(R.layout.row_notification, null);
            viewHolder = new ViewHolder();

            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        int i = data.get(position);

        return row;
    }

    private class ViewHolder {

    }

    public void addItem(final Integer item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }
}
