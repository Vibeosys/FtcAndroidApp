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
 * Created by akshay on 16-06-2016.
 */
public class LoginSpinner extends BaseAdapter {

    private ArrayList<String> mData;
    private Context mContext;

    public LoginSpinner(ArrayList<String> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
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
            row = theLayoutInflator.inflate(R.layout.row_spinner_item, null);
            viewHolder = new ViewHolder();
            viewHolder.spinnerName = (TextView) row.findViewById(R.id.spinner_name);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.spinnerName.setText(mData.get(position));

        return row;
    }

    private class ViewHolder {
        TextView spinnerName;
    }
}
