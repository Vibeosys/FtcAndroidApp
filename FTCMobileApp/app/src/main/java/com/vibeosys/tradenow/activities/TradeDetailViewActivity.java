package com.vibeosys.tradenow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.adapterdata.SignalDataDTO;
import com.vibeosys.tradenow.data.adapterdata.TradeBackupDataDTO;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.Date;

public class TradeDetailViewActivity extends BaseActivity {


    private TextView /*txtMasterAccNo,*/ txtTicketNo, txtLotSize, txtPrice, txtClosePrice, txtSp,
            txtTp, txtSwap, txtProfit, txtProfitLoss, txtOpenTime, txtCloseTime;
    private long ticketNo;
    private DateUtils dateUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_detail_view);
        setTitle("INR TO USD");
        ticketNo = getIntent().getExtras().getLong("ticketNo");
        //txtMasterAccNo = (TextView) findViewById(R.id.txtMasterAccNo);
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

        TradeBackupDataDTO signalDataDTO = mDbRepository.getTradeBackUp(ticketNo);
        updateUi(signalDataDTO);
    }

    private void updateUi(TradeBackupDataDTO traeBackupDTO) {
        //txtMasterAccNo.setText("");
        txtTicketNo.setText("" + traeBackupDTO.getTicket());
        txtLotSize.setText("" + traeBackupDTO.getLot());
        txtPrice.setText("" + traeBackupDTO.getPrice());
        txtClosePrice.setText("" + traeBackupDTO.getClosePrice());
        txtSp.setText("" + traeBackupDTO.getSl());
        txtTp.setText("" + traeBackupDTO.getTp());
        txtSwap.setText("" + traeBackupDTO.getSwap());
        txtProfit.setText("" + traeBackupDTO.getProfit());
        txtProfitLoss.setText("" + traeBackupDTO.getPips());
        DateUtils dateUtils = new DateUtils();
        Date signalDate = dateUtils.getFormattedDate(traeBackupDTO.getOpenTime());
        Date closeDate = dateUtils.getFormattedDate(traeBackupDTO.getCloseTime());
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
