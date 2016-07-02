package com.vibeosys.tradenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.adapterdata.SignalDataDTO;
import com.vibeosys.tradenow.data.adapterdata.TradeBackupDataDTO;
import com.vibeosys.tradenow.data.adapterdata.TradeBackupDateDTO;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akshay on 14-06-2016.
 */
public class TradeHistoryAdapter extends BaseAdapter {

    private ArrayList<TradeBackupDataDTO> data;
    private Context mContext;
    ViewDetailsListener viewDetailsListener;
    DateUtils dateUtils = new DateUtils();

    public TradeHistoryAdapter(ArrayList<TradeBackupDataDTO> data, Context mContext) {
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
            viewHolder.txtProfitOrLoss = (TextView) row.findViewById(R.id.txtBuyOrSell);
            viewHolder.txtViewAll = (TextView) row.findViewById(R.id.txtViewAll);
            viewHolder.txtClosePrice = (TextView) row.findViewById(R.id.txtClosePrice);
            viewHolder.txtType = (TextView) row.findViewById(R.id.txtType);
            viewHolder.txtDate = (TextView) row.findViewById(R.id.txtDate);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        final TradeBackupDataDTO tradeBackupDataDTO = data.get(position);
        String symbol = tradeBackupDataDTO.getSymbol();
        if (symbol.length() == 6) {
            StringBuffer sb = new StringBuffer(symbol);
            sb.insert(3, " / ");
            symbol = sb.toString();
        }
        viewHolder.txtType.setText(symbol);
        viewHolder.txtPrice.setText("" + tradeBackupDataDTO.getPrice());
        viewHolder.txtSL.setText("" + tradeBackupDataDTO.getSl());
        viewHolder.txtPlPip.setText("" + tradeBackupDataDTO.getProfit());
        viewHolder.txtTP.setText("" + tradeBackupDataDTO.getTp());

        Date openDate = dateUtils.getFormattedDate(tradeBackupDataDTO.getOpenTime());
        viewHolder.txtTime.setText(dateUtils.getLocalTimeInReadableFormat(openDate));

        Date closeDate = dateUtils.getFormattedDate(tradeBackupDataDTO.getCloseTime());
        viewHolder.txtCloseTime.setText(dateUtils.getLocalTimeInReadableFormat(closeDate));
        viewHolder.txtClosePrice.setText("" + tradeBackupDataDTO.getClosePrice());
        viewHolder.txtDate.setText(dateUtils.getLocalDateInReadableFormat(closeDate)+" "+dateUtils.getLocalTimeInReadableFormat(closeDate));
        if (tradeBackupDataDTO.getProfit() < 0) {
            viewHolder.txtProfitOrLoss.setTextColor(mContext.getResources().getColor(R.color.cancel_btn_colour));
            viewHolder.txtProfitOrLoss.setText("LOSS");
        } else {
            viewHolder.txtProfitOrLoss.setTextColor(mContext.getResources().getColor(R.color.ok_btn_colour));
            viewHolder.txtProfitOrLoss.setText("PROFIT");
        }
        viewHolder.txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewDetailsListener != null)
                    viewDetailsListener.onViewClickListener(v.getId(), tradeBackupDataDTO.getTicket(), new Object());
            }
        });

        return row;
    }

    private class ViewHolder {
        TextView txtPrice, txtSL, txtPlPip, txtTP, txtTime, txtCloseTime, txtProfitOrLoss,
                txtViewAll, txtClosePrice, txtType, txtDate;
    }

    public void addItem(final TradeBackupDataDTO item) {
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
