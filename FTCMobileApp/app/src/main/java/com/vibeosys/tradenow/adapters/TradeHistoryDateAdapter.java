package com.vibeosys.tradenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.adapterdata.SignalDateDTO;
import com.vibeosys.tradenow.data.adapterdata.TradeBackupDateDTO;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akshay on 14-06-2016.
 */
public class TradeHistoryDateAdapter extends BaseAdapter {

    private static final String TAG = TradeHistoryDateAdapter.class.getSimpleName();
    private ArrayList<TradeBackupDateDTO> data;
    private Context mContext;

    public TradeHistoryDateAdapter(ArrayList<TradeBackupDateDTO> data, Context mContext) {
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
            row = theLayoutInflator.inflate(R.layout.row_trade_date, null);
            viewHolder = new ViewHolder();
            viewHolder.txtDate = (TextView) row.findViewById(R.id.txtDate);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        TradeBackupDateDTO tradeDateDTO = data.get(position);
        /*long tradeDate = tradeDateDTO.getDate() * 1000;*/
        //Log.d(TAG, "## date time stamp" + tradeDate);
        //Date tDate = new Date(tradeDate);
        DateUtils dateUtils = new DateUtils();
        Date signalDate = dateUtils.getFormattedOnlyDate(tradeDateDTO.getDate());
        viewHolder.txtDate.setText(dateUtils.getLocalDateInReadableFormat(signalDate));
        return row;
    }

    private class ViewHolder {
        TextView txtDate;
    }

    public void addItem(final TradeBackupDateDTO item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }

}
