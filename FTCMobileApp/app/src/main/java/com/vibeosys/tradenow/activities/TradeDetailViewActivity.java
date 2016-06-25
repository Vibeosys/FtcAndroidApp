package com.vibeosys.tradenow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.adapterdata.SignalDataDTO;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.Date;

public class TradeDetailViewActivity extends BaseActivity {


    private TextView txtMasterAccNo, txtTicketNo, txtLotSize, txtPrice, txtClosePrice, txtSp,
            txtTp, txtSwap, txtProfit, txtProfitLoss, txtOpenTime, txtCloseTime;
    private long ticketNo;
    private DateUtils dateUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_detail_view);
        setTitle("INR TO USD");
        ticketNo = getIntent().getExtras().getLong("ticketNo");
        txtMasterAccNo = (TextView) findViewById(R.id.txtMasterAccNo);
        txtTicketNo = (TextView) findViewById(R.id.txtTicketNo);
        txtLotSize = (TextView) findViewById(R.id.txtLotSize);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtClosePrice = (TextView) findViewById(R.id.txtClosePrice);
        txtSp = (TextView) findViewById(R.id.txtSp);
        txtTp = (TextView) findViewById(R.id.txtTp);
        txtSwap = (TextView) findViewById(R.id.txtSwap);
        txtProfit = (TextView) findViewById(R.id.txtProfit);
        txtProfitLoss = (TextView) findViewById(R.id.txtProfitLoss);
        txtOpenTime = (TextView) findViewById(R.id.txtOpenTime);
        txtCloseTime = (TextView) findViewById(R.id.txtCloseTime);

        SignalDataDTO signalDataDTO = mDbRepository.getSignalData(ticketNo);
        updateUi(signalDataDTO);
    }

    private void updateUi(SignalDataDTO signalDataDTO) {
        txtMasterAccNo.setText("");
        txtTicketNo.setText("" + signalDataDTO.getTicket());
        txtLotSize.setText("" + signalDataDTO.getLot());
        txtPrice.setText("" + signalDataDTO.getPrice());
        txtClosePrice.setText("" + signalDataDTO.getClosePrice());
        txtSp.setText("" + signalDataDTO.getSl());
        txtTp.setText("" + signalDataDTO.getTp());
        txtSwap.setText("" + signalDataDTO.getSwap());
        txtProfit.setText("" + signalDataDTO.getProfit());
        txtProfitLoss.setText("" + signalDataDTO.getProfit());
        DateUtils dateUtils = new DateUtils();
        Date signalDate = dateUtils.getFormattedDate(signalDataDTO.getOpenTime());
        Date closeDate = dateUtils.getFormattedDate(signalDataDTO.getExpTime());
        txtOpenTime.setText("" + dateUtils.getLocalDateInReadableFormat(signalDate) + " "
                + dateUtils.getLocalTimeInReadableFormat(signalDate));
        txtCloseTime.setText("" + dateUtils.getLocalDateInReadableFormat(closeDate) + " "
                + dateUtils.getLocalTimeInReadableFormat(closeDate));
    }

    @Override
    protected View getMainView() {
        return null;
    }
}
