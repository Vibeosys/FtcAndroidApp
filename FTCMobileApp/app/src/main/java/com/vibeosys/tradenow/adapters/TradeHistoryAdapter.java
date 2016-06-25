package com.vibeosys.tradenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.adapterdata.SignalDataDTO;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akshay on 14-06-2016.
 */
public class TradeHistoryAdapter extends BaseAdapter {

    private ArrayList<SignalDataDTO> data;
    private Context mContext;
    ViewDetailsListener viewDetailsListener;
    DateUtils dateUtils = new DateUtils();

    public TradeHistoryAdapter(ArrayList<SignalDataDTO> data, Context mContext) {
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
            viewHolder.txtPlPip = (TextView) row.findViewById(R.id.txtPlPip);
            viewHolder.txtTP = (TextView) row.findViewById(R.id.txtTP);
            viewHolder.txtTime = (TextView) row.findViewById(R.id.txtTime);
            viewHolder.txtCloseTime = (TextView) row.findViewById(R.id.txtCloseTime);
            viewHolder.txtBuyOrSell = (TextView) row.findViewById(R.id.txtBuyOrSell);
            viewHolder.txtViewAll = (TextView) row.findViewById(R.id.txtViewAll);
            viewHolder.txtClosePrice = (TextView) row.findViewById(R.id.txtClosePrice);
            viewHolder.txtType = (TextView) row.findViewById(R.id.txtType);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        final SignalDataDTO signalDataDTO = data.get(position);
        String symbol = signalDataDTO.getSymbol();
        if (symbol.length() == 6) {
            StringBuffer sb = new StringBuffer(symbol);
            sb.insert(3, " To ");
            symbol = sb.toString();
        }
        viewHolder.txtType.setText(symbol);
        viewHolder.txtPrice.setText("" + signalDataDTO.getPrice());
        viewHolder.txtSL.setText("" + signalDataDTO.getSl());
        viewHolder.txtPlPip.setText("" + signalDataDTO.getProfit());
        viewHolder.txtTP.setText("" + signalDataDTO.getTp());

        Date openDate = dateUtils.getFormattedDate(signalDataDTO.getOpenTime());
        viewHolder.txtTime.setText(dateUtils.getLocalTimeInReadableFormat(openDate));

        Date closeDate = dateUtils.getFormattedDate(signalDataDTO.getCloseTime());
        viewHolder.txtCloseTime.setText(dateUtils.getLocalTimeInReadableFormat(closeDate));
        viewHolder.txtClosePrice.setText("" + signalDataDTO.getClosePrice());
        /*if (i % 2 == 0) {
            viewHolder.txtBuyOrSell.setTextColor(mContext.getResources().getColor(R.color.cancel_btn_colour));
            viewHolder.txtBuyOrSell.setText("LOSS");
        } else {
            viewHolder.txtBuyOrSell.setTextColor(mContext.getResources().getColor(R.color.ok_btn_colour));
            viewHolder.txtBuyOrSell.setText("PROFIT");
        }*/
        viewHolder.txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewDetailsListener != null)
                    viewDetailsListener.onViewClickListener(v.getId(), signalDataDTO.getTicket(), new Object());
            }
        });
        return row;
    }

    private class ViewHolder {
        TextView txtPrice, txtSL, txtPlPip, txtTP, txtTime, txtCloseTime, txtBuyOrSell,
                txtViewAll, txtClosePrice, txtType;
    }

    public void addItem(final SignalDataDTO item) {
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
        public void onViewClickListener(int id, long value, Object object);
    }
}
