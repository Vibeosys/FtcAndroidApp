package com.vibeosys.tradenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.shuttercomponent.ShutterHeader;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.adapterdata.SignalDataDTO;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by akshay on 14-06-2016.
 */
public class TradeAlertAdapter extends BaseAdapter {

    private ArrayList<SignalDataDTO> data;
    private Context mContext;
    ViewDetailsListener viewDetailsListener;

    public TradeAlertAdapter(ArrayList<SignalDataDTO> data, Context mContext) {
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
            viewHolder.txtType = (TextView) row.findViewById(R.id.txtType);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        SignalDataDTO signalDataDTO = data.get(position);
        String symbol = signalDataDTO.getSymbol();
        if (symbol.length() == 6) {
            StringBuffer sb = new StringBuffer(symbol);
            sb.insert(3, " To ");
            symbol = sb.toString();
        }

        viewHolder.txtPrice.setText("" + signalDataDTO.getPrice());
        viewHolder.txtSL.setText("" + signalDataDTO.getSl());
        viewHolder.txtLotSize.setText("" + signalDataDTO.getLot());
        viewHolder.txtTP.setText("" + signalDataDTO.getTp());
        viewHolder.txtType.setText(symbol);
        DateUtils dateUtils = new DateUtils();
        Date openDate = dateUtils.getFormattedDate(signalDataDTO.getOpenTime());
        viewHolder.txtTime.setText(dateUtils.getLocalTimeInReadableFormat(openDate));
        if (signalDataDTO.getSType() == 0) {
            viewHolder.txtBuyOrSell.setTextColor(mContext.getResources().getColor(R.color.cancel_btn_colour));
            viewHolder.txtBuyOrSell.setText("SELL");
            /*viewHolder.shutterHeader.setTxtBuyOrSell("SELL");
            viewHolder.shutterHeader.setTxtBuyOrSellColor(mContext.getResources().getColor(R.color.cancel_btn_colour));*/
        } else if (signalDataDTO.getSType() == 1) {
            viewHolder.txtBuyOrSell.setTextColor(mContext.getResources().getColor(R.color.ok_btn_colour));
            viewHolder.txtBuyOrSell.setText("BUY");
           /* viewHolder.shutterHeader.setTxtBuyOrSell("BUY");
            viewHolder.shutterHeader.setTxtBuyOrSellColor(mContext.getResources().getColor(R.color.ok_btn_colour));*/
        }

        return row;
    }

    private class ViewHolder {
        TextView txtPrice, txtSL, txtLotSize, txtTP, txtTime, txtBuyOrSell, txtType;

        //ShutterHeader shutterHeader;
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
        public void onViewClickListener(int id, int value, Object object);
    }
}
