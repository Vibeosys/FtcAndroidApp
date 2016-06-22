package com.vibeosys.tradenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.shuttercomponent.ShutterHeader;
import com.vibeosys.tradenow.R;

import java.util.ArrayList;

/**
 * Created by akshay on 14-06-2016.
 */
public class TradeAlertAdapter extends BaseAdapter {

    private ArrayList<Integer> data;
    private Context mContext;
    ViewDetailsListener viewDetailsListener;

    public TradeAlertAdapter(ArrayList<Integer> data, Context mContext) {
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
            //viewHolder.shutterHeader = (ShutterHeader) row.findViewById(R.id.txtHeader);
            viewHolder.txtPrice = (TextView) row.findViewById(R.id.txtPrice);
            viewHolder.txtSL = (TextView) row.findViewById(R.id.txtSL);
            viewHolder.txtLotSize = (TextView) row.findViewById(R.id.txtLotSize);
            viewHolder.txtTP = (TextView) row.findViewById(R.id.txtTP);
            viewHolder.txtTime = (TextView) row.findViewById(R.id.txtTime);
            viewHolder.txtBuyOrSell = (TextView) row.findViewById(R.id.txtBuyOrSell);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        int i = data.get(position);
       /* viewHolder.shutterHeader.setTxtPrice("0.84875");
        viewHolder.shutterHeader.setTxtSL("0.84975");
        viewHolder.shutterHeader.setLotSize("0.1");
        viewHolder.shutterHeader.setTP("0.85725");
        viewHolder.shutterHeader.setTime("09:30 AM");
        viewHolder.shutterHeader.setCloseTime("03:30 PM");
        viewHolder.shutterHeader.setLayoutVisibility(View.GONE);
        viewHolder.shutterHeader.setImageVisibility(View.VISIBLE);
        viewHolder.shutterHeader.txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewDetailsListener != null)
                    viewDetailsListener.onViewClickListener(v.getId(), 1, new Object());
            }
        });*/
        viewHolder.txtPrice.setText("0.84875");
        viewHolder.txtSL.setText("0.84975");
        viewHolder.txtLotSize.setText("0.1");
        viewHolder.txtTP.setText("0.85725");
        viewHolder.txtTime.setText("09:30 AM");
        if (i % 2 == 0) {
            viewHolder.txtBuyOrSell.setTextColor(mContext.getResources().getColor(R.color.cancel_btn_colour));
            viewHolder.txtBuyOrSell.setText("SELL");
            /*viewHolder.shutterHeader.setTxtBuyOrSell("SELL");
            viewHolder.shutterHeader.setTxtBuyOrSellColor(mContext.getResources().getColor(R.color.cancel_btn_colour));*/
        } else {
            viewHolder.txtBuyOrSell.setTextColor(mContext.getResources().getColor(R.color.ok_btn_colour));
            viewHolder.txtBuyOrSell.setText("BUY");
           /* viewHolder.shutterHeader.setTxtBuyOrSell("BUY");
            viewHolder.shutterHeader.setTxtBuyOrSellColor(mContext.getResources().getColor(R.color.ok_btn_colour));*/
        }

        return row;
    }

    private class ViewHolder {
        TextView txtPrice, txtSL, txtLotSize, txtTP, txtTime, txtBuyOrSell;

        //ShutterHeader shutterHeader;
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
