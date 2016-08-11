package com.ftcsolutions.tradenow.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.data.adapterdata.TradeBackupDataDTO;
import com.ftcsolutions.tradenow.utils.DateUtils;

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

    private void updateUi(TradeBackupDataDTO tradeBackupDTO) {
        //txtMasterAccNo.setText("");
        String symbol = tradeBackupDTO.getSymbol();
        if (symbol.length() == 6) {
            StringBuffer sb = new StringBuffer(symbol);
            sb.insert(3, " TO ");
            symbol = sb.toString();
        }
        setTitle(symbol);
        txtTicketNo.setText("" + tradeBackupDTO.getTicket());
        txtLotSize.setText("" + tradeBackupDTO.getLot());
        txtPrice.setText("" + tradeBackupDTO.getPrice());
        txtClosePrice.setText("" + tradeBackupDTO.getClosePrice());
        txtSp.setText("" + tradeBackupDTO.getSl());
        txtTp.setText("" + tradeBackupDTO.getTp());
        txtSwap.setText("" + tradeBackupDTO.getSwap());
        txtProfit.setText("" + tradeBackupDTO.getProfit());
        txtProfitLoss.setText("" + tradeBackupDTO.getPips());
        DateUtils dateUtils = new DateUtils();
        Date signalDate = dateUtils.getFormattedDate(tradeBackupDTO.getOpenTime());
        Date closeDate = dateUtils.getFormattedDate(tradeBackupDTO.getCloseTime());
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
