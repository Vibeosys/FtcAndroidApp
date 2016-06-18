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
public class TradeHistoryAdapter extends BaseAdapter {

    private ArrayList<Integer> data;
    private Context mContext;
    ViewDetailsListener viewDetailsListener;

    public TradeHistoryAdapter(ArrayList<Integer> data, Context mContext) {
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
            row = theLayoutInflator.inflate(R.layout.row_trade_history, null);
            viewHolder = new ViewHolder();
            viewHolder.txtPrice = (TextView) row.findViewById(R.id.txtPrice);
            viewHolder.txtSL = (TextView) row.findViewById(R.id.txtSL);
            viewHolder.txtLotSize = (TextView) row.findViewById(R.id.txtLotSize);
            viewHolder.txtTP = (TextView) row.findViewById(R.id.txtTP);
            viewHolder.txtTime = (TextView) row.findViewById(R.id.txtTime);
            viewHolder.txtCloseTime = (TextView) row.findViewById(R.id.txtCloseTime);
            viewHolder.txtBuyOrSell = (TextView) row.findViewById(R.id.txtBuyOrSell);
            viewHolder.txtViewAll = (TextView) row.findViewById(R.id.txtViewAll);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        int i = data.get(position);
        viewHolder.txtPrice.setText("0.84875");
        viewHolder.txtSL.setText("0.84975");
        viewHolder.txtLotSize.setText("0.1");
        viewHolder.txtTP.setText("0.85725");
        viewHolder.txtTime.setText("09:30 AM");
        viewHolder.txtCloseTime.setText("03:30 PM");
        if (i % 2 == 0) {
            viewHolder.txtBuyOrSell.setTextColor(mContext.getResources().getColor(R.color.cancel_btn_colour));
            viewHolder.txtBuyOrSell.setText("LOSS");
        } else {
            viewHolder.txtBuyOrSell.setTextColor(mContext.getResources().getColor(R.color.ok_btn_colour));
            viewHolder.txtBuyOrSell.setText("PROFIT");
        }
        viewHolder.txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewDetailsListener != null)
                    viewDetailsListener.onViewClickListener(v.getId(), 1, new Object());
            }
        });
        return row;
    }

    private class ViewHolder {
        TextView txtPrice, txtSL, txtLotSize, txtTP, txtTime, txtCloseTime, txtBuyOrSell, txtViewAll;
    }

    public void addItem(final Integer item) {
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
