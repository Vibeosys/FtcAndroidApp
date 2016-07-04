package com.vibeosys.tradenow.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.adapterdata.NotificationsDTO;
import com.vibeosys.tradenow.database.DbRepository;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akshay on 14-06-2016.
 */
public class NotificationAdapter extends BaseAdapter {

    private ArrayList<NotificationsDTO> data = null;
    private Context mContext;
    private DateUtils dateUtils = new DateUtils();
    private DbRepository mDbRepository;

    public NotificationAdapter(ArrayList<NotificationsDTO> data, Context mContext, DbRepository dbRepository) {
        this.data = data;
        this.mContext = mContext;
        this.mDbRepository = dbRepository;
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
            viewHolder.card_view = (CardView) row.findViewById(R.id.card_view);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        NotificationsDTO notificationsDTO = data.get(position);
        viewHolder.txtHeading.setText(notificationsDTO.getmNotificationTitle());
        viewHolder.txtDescription.setText(notificationsDTO.getmNotificationDesc());
        Date closeDate = dateUtils.getFormattedDate(notificationsDTO.getmNotificationDate());
        viewHolder.txtDate.setText(dateUtils.getLocalDateInReadableFormat(closeDate) + " "
                + dateUtils.getLocalTimeInReadableFormat(closeDate));
        if (notificationsDTO.getmIsRead() == 0) {
            viewHolder.card_view.setBackgroundColor(mContext.getResources().getColor(R.color.unread_notifications));
        } else if (notificationsDTO.getmIsRead() == 1) {
            viewHolder.card_view.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
        }
        return row;
    }

    private class ViewHolder {
        TextView txtHeading, txtDescription, txtDate;
        CardView card_view;
    }

    public void addItem(final NotificationsDTO item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }

    public void refresh() {
        if (this.data != null) {
            this.data.clear();
        }
        this.data.addAll(mDbRepository.getNotification());
        notifyDataSetChanged();
    }
}
