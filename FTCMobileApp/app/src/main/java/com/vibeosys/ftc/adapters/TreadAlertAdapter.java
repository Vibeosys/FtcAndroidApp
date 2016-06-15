package com.vibeosys.ftc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.ftc.R;

import java.util.ArrayList;

/**
 * Created by akshay on 14-06-2016.
 */
public class TreadAlertAdapter extends BaseAdapter {

    private ArrayList<Integer> data;
    private Context mContext;

    public TreadAlertAdapter(ArrayList<Integer> data, Context mContext) {
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
            row = theLayoutInflator.inflate(R.layout.row_trade_alert, null);
            viewHolder = new ViewHolder();
            viewHolder.txtPrice = (TextView) row.findViewById(R.id.txtPrice);
            viewHolder.txtClosePrice = (TextView) row.findViewById(R.id.txtClosePrice);
            viewHolder.txtDate = (TextView) row.findViewById(R.id.txtDate);
            viewHolder.txtCloseDate = (TextView) row.findViewById(R.id.txtCloseDate);
            viewHolder.txtTime = (TextView) row.findViewById(R.id.txtTime);
            viewHolder.txtCloseTime = (TextView) row.findViewById(R.id.txtCloseTime);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.txtPrice.setText("50.00");
        viewHolder.txtClosePrice.setText("60.00");
        viewHolder.txtDate.setText("14 Jun 2016");
        viewHolder.txtCloseDate.setText("16 Jun 2016");
        viewHolder.txtTime.setText("09:30 AM");
        viewHolder.txtCloseTime.setText("03:30 PM");
        return row;
    }

    private class ViewHolder {
        TextView txtPrice, txtClosePrice, txtDate, txtCloseDate, txtTime, txtCloseTime;
    }

    public void addItem(final Integer item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }
}
