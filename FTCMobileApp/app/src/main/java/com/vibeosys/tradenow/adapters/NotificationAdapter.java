package com.vibeosys.tradenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.adapterdata.NotificationsDTO;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akshay on 14-06-2016.
 */
public class NotificationAdapter extends BaseAdapter {

    private ArrayList<NotificationsDTO> data = null;
    private Context mContext;
    DateUtils dateUtils = new DateUtils();

    public NotificationAdapter(ArrayList<NotificationsDTO> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        else
            return 0;
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
            viewHolder.txtHeading = (TextView) row.findViewById(R.id.txtHeading);
            viewHolder.txtDescription = (TextView) row.findViewById(R.id.txtDescription);
            viewHolder.txtDate = (TextView) row.findViewById(R.id.txtDate);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        NotificationsDTO notificationsDTO = data.get(position);
        viewHolder.txtHeading.setText(notificationsDTO.getmNotificationTitle());
        viewHolder.txtDescription.setText(notificationsDTO.getmNotificationDesc());
        Date closeDate = dateUtils.getFormattedDate(notificationsDTO.getmNotificationDate());
        viewHolder.txtDate.setText(dateUtils.getLocalDateInReadableFormat(closeDate) + " "
                + dateUtils.getLocalTimeInReadableFormat(closeDate));
        return row;
    }

    private class ViewHolder {
        TextView txtHeading, txtDescription, txtDate;
    }

    public void addItem(final NotificationsDTO item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }
}
