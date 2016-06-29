package com.vibeosys.tradenow.database;

/**
 * Created by akshay on 22-06-2016.
 */
public class SqlContract {

    public abstract class SqlSignal {
        public static final String TABLE_NAME = "signal";
        public static final String TICKET = "Ticket";
        public static final String SYMBOL = "Symbol";
        public static final String S_TYPE = "sType";
        public static final String LOT = "lot";
        public static final String PRICE = "price";
        public static final String SL = "sl";
        public static final String TP = "tp";
        public static final String CLOSE_PRICE = "close_price";
        public static final String SWAP = "swap";
        public static final String PROFIT = "profit";
        public static final String OPEN_TIME = "open_time";
        public static final String CLOSE_TIME = "close_time";
        public static final String STATUS = "status";
        public static final String COPY = "copy";
        public static final String EXP_TIME = "exp_time";
        public static final String SIGNAL_DATE = "signal_date";
    }

    public abstract class SqlTradeBackUp {
        public static final String TABLE_NAME = "trade_backup";
        public static final String MASTER_ACC_ID = "masteraccountid";
        public static final String TICKET = "Ticket";
        public static final String SYMBOL = "Symbol";
        public static final String S_TYPE = "sType";
        public static final String LOT = "lot";
        public static final String PRICE = "price";
        public static final String SL = "sl";
        public static final String TP = "tp";
        public static final String CLOSE_PRICE = "close_price";
        public static final String SWAP = "swap";
        public static final String PROFIT = "profit";
        public static final String OPEN_TIME = "open_time";
        public static final String CLOSE_TIME = "close_time";
        public static final String STATUS = "status";
        public static final String PL_IN_PIPS = "pl_pips";
        public static final String TRADE_BACK_DATE = "tradeBackupDate";
    }

    public abstract class SqlPageType {
        public static final String TABLE_NAME = "mobile_page_type";
        public static final String PAGE_TYPE_ID = "PageTypeId";
        public static final String PAGET_TYPE_DESC = "PageTypeDesc";
        public static final String ACTIVE = "Active";
    }

    public abstract class SqlPage {
        public static final String TABLE_NAME = "mobile_pages";
        public static final String PAGE_ID = "PageId";
        public static final String PAGE_TITLE = "PageTitle";
        public static final String PAGE_STATUS = "Status";
        public static final String PAGE_TYPE_ID = "PageTypeId";
        public static final String ACTIVE = "Active";
    }

    public abstract class SqlWidget {
        public static final String TABLE_NAME = "widget";
        public static final String WIDGET_ID = "WidgetId";
        public static final String WIDGET_TITLE = "WidgetTitle";
        public static final String POSITION = "Position";
        public static final String WIDGET_DATA = "WidgetData";
        public static final String ACTIVE = "Active";
    }
}
