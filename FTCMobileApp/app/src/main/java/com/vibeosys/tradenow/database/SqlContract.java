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
    }
}
