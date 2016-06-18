package com.vibeosys.shuttercomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by akshay on 17-06-2016.
 */
public class ShutterHeader extends LinearLayout implements View.OnClickListener {

    private TextView txtType, txtPrice, txtSL, txtLotSize, txtTP, txtTime, txtCloseTime, txtBuyOrSell;
    private Context mContext;
    private LinearLayout linearLayout;
    private ImageView imgDown, imgUp;
    public TextView txtViewAll;

    public ShutterHeader(Context context) {
        super(context);
    }

    public ShutterHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public ShutterHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        inflate(mContext, R.layout.shutter_header, this);
        txtType = (TextView) findViewById(R.id.txtType);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtSL = (TextView) findViewById(R.id.txtSL);
        txtLotSize = (TextView) findViewById(R.id.txtLotSize);
        txtTP = (TextView) findViewById(R.id.txtTP);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtCloseTime = (TextView) findViewById(R.id.txtCloseTime);
        txtBuyOrSell = (TextView) findViewById(R.id.txtBuyOrSell);
        linearLayout = (LinearLayout) findViewById(R.id.hideLayout);
        txtViewAll = (TextView) findViewById(R.id.txtViewAll);
        imgDown = (ImageView) findViewById(R.id.downArrow);
        imgUp = (ImageView) findViewById(R.id.upArrow);
        imgDown.setOnClickListener(this);
        imgUp.setOnClickListener(this);
    }

    public void setTxtType(String type) {
        txtType.setText(type);
    }

    public void setTxtPrice(String price) {
        txtPrice.setText(price);
    }

    public void setTxtSL(String sl) {
        txtSL.setText(sl);
    }

    public void setLotSize(String lotSize) {
        txtLotSize.setText(lotSize);
    }

    public void setTP(String TP) {
        txtTP.setText(TP);
    }

    public void setTime(String time) {
        txtTime.setText(time);
    }

    public void setCloseTime(String closeTime) {
        txtCloseTime.setText(closeTime);
    }

    public void setTxtBuyOrSell(String type) {
        txtBuyOrSell.setText(type);
    }

    public void setTxtBuyOrSellColor(int color) {
        txtBuyOrSell.setTextColor(color);
    }

    public String getTxtType() {
        return txtType.getText().toString();
    }

    public String getTxtPrice() {
        return txtPrice.getText().toString();
    }

    public String getTxtSL(String sl) {
        return txtSL.getText().toString();
    }

    public String getLotSize() {
        return txtLotSize.getText().toString();
    }

    public String getTP() {
        return txtTP.getText().toString();
    }

    public String getTime() {
        return txtTime.getText().toString();
    }

    public String getCloseTime() {
        return txtCloseTime.getText().toString();
    }

    public String gsetTxtBuyOrSell() {
        return txtBuyOrSell.getText().toString();
    }

    public void setImageVisibility(int visibility) {
        imgDown.setVisibility(visibility);
    }

    public void setLayoutVisibility(int visibility) {
        linearLayout.setVisibility(visibility);
    }

    public int getLayoutVisibility() {
        return linearLayout.getVisibility();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.downArrow) {
            imgDown.setVisibility(INVISIBLE);
            linearLayout.setVisibility(VISIBLE);
            return;
        }
        if (id == R.id.upArrow) {
            linearLayout.setVisibility(GONE);
            imgDown.setVisibility(VISIBLE);
        }
       /* if (linearLayout.getVisibility() == INVISIBLE || linearLayout.getVisibility() == GONE)
            linearLayout.setVisibility(VISIBLE);
        else linearLayout.setVisibility(GONE);*/
    }
}
