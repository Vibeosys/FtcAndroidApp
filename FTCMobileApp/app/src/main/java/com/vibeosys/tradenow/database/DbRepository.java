package com.vibeosys.tradenow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.vibeosys.tradenow.data.adapterdata.SignalDataDTO;
import com.vibeosys.tradenow.data.adapterdata.SignalDateDTO;
import com.vibeosys.tradenow.data.adapterdata.TradeBackupDataDTO;
import com.vibeosys.tradenow.data.adapterdata.TradeBackupDateDTO;
import com.vibeosys.tradenow.data.responsedata.ResponsePageData;
import com.vibeosys.tradenow.data.responsedata.ResponsePageType;
import com.vibeosys.tradenow.data.responsedata.ResponseSignalDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseTradeBackUp;
import com.vibeosys.tradenow.data.responsedata.ResponseWidgetData;
import com.vibeosys.tradenow.utils.DateUtils;
import com.vibeosys.tradenow.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 22-06-2016.
 */
public class DbRepository extends SQLiteOpenHelper {

    private static final String TAG = DbRepository.class.getSimpleName();

    private final static String DATABASE_NAME = "tradenow.db";
    private final String CREATE_SIGNAL = "CREATE TABLE IF NOT EXISTS signal (" +
            "  Ticket int(10) NOT NULL," +
            "  Symbol varchar(45) DEFAULT NULL," +
            "  sType int(10) DEFAULT NULL," +
            "  lot double(17,10) DEFAULT NULL," +
            "  price double(17,10) DEFAULT NULL," +
            "  sl double(17,10) DEFAULT NULL," +
            "  tp double(17,10) DEFAULT NULL," +
            "  close_price double(17,10) DEFAULT NULL," +
            "  swap double(17,10) DEFAULT NULL," +
            "  profit double(17,10) DEFAULT NULL," +
            "  open_time datetime DEFAULT NULL," +
            "  close_time datetime DEFAULT NULL," +
            "  status varchar(25) DEFAULT NULL," +
            "  copy int(10) DEFAULT NULL," +
            "  exp_time datetime DEFAULT NULL,signal_date datetime DEFAULT NULL," +
            "  PRIMARY KEY (Ticket)" +
            ");";

    private final String CREATE_TRADE_BACKUP = "CREATE TABLE IF NOT EXISTS trade_backup (" +
            "  masteraccountid int(11) NOT NULL DEFAULT '0'," +
            "  Ticket int(10) DEFAULT NULL," +
            "  Symbol varchar(45) DEFAULT NULL," +
            "  sType int(10) DEFAULT NULL," +
            "  lot double(17,10) DEFAULT NULL," +
            "  price double(17,10) DEFAULT NULL," +
            "  sl double(17,10) DEFAULT NULL," +
            "  tp double(17,10) DEFAULT NULL," +
            "  close_price double(17,10) DEFAULT NULL," +
            "  swap double(17,10) DEFAULT NULL," +
            "  profit double(17,10) DEFAULT NULL," +
            "  open_time datetime DEFAULT NULL," +
            "  close_time datetime DEFAULT NULL," +
            "  status varchar(25) DEFAULT NULL," +
            "  pl_pips int(11) DEFAULT NULL,tradeBackupDate datetime DEFAULT NULL" +
            ") ";


    private final String CREATE_PAGE_TYPE = "CREATE TABLE IF NOT EXISTS mobile_page_type (" +
            " PageTypeId INT NOT NULL," +
            " PageTypeDesc VARCHAR(45) NULL," +
            " Active INT(1) NULL," +
            " PRIMARY KEY (PageTypeId));";

    private final String CREATE_PAGE = "CREATE TABLE IF NOT EXISTS mobile_pages(" +
            "  PageId VARCHAR(50) NOT NULL ," +
            "  PageTitle VARCHAR(45) NOT NULL," +
            "  Status INT(1) NULL," +
            "  PageTypeId INT NULL," +
            "  CreatedDate DATETIME NULL," +
            "  UpdatedDate DATETIME NULL," +
            "  Active INT(1) NULL," +
            "  PRIMARY KEY (PageId));";

    private final String CREATE_WIDGET = "CREATE TABLE widget (" +
            "  WidgetId INT NOT NULL," +
            "  WidgetTitle VARCHAR(45) NOT NULL ," +
            "  Position INT NULL," +
            "  WidgetData TEXT NULL ," +
            "  PageId VARCHAR(50) NOT NULL," +
            "  PRIMARY KEY (WidgetId));";

    public DbRepository(Context context, SessionManager sessionManager) {
        super(context, DATABASE_NAME, null, sessionManager.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_SIGNAL);
            Log.d(TAG, "##Signal Table Create " + CREATE_SIGNAL);
        } catch (SQLiteException e) {
            Log.e(TAG, "##Could not create signal table" + e.toString());
        }
        try {
            db.execSQL(CREATE_TRADE_BACKUP);
            Log.d(TAG, "##Signal Table Create " + CREATE_SIGNAL);
        } catch (SQLiteException e) {
            Log.e(TAG, "##Could not create signal table" + e.toString());
        }
        try {
            db.execSQL(CREATE_PAGE_TYPE);
            Log.d(TAG, "##Page type Table Create " + CREATE_PAGE_TYPE);
        } catch (SQLiteException e) {
            Log.e(TAG, "##Could not create Page type table" + e.toString());
        }
        try {
            db.execSQL(CREATE_PAGE);
            Log.d(TAG, "##Page Table Create " + CREATE_PAGE);
        } catch (SQLiteException e) {
            Log.e(TAG, "##Could not create Page Table" + e.toString());
        }

        try {
            db.execSQL(CREATE_WIDGET);
            Log.d(TAG, "##Widget Table created" + CREATE_WIDGET);
        } catch (SQLiteException e) {
            Log.e(TAG, "##could not create widget table" + e.toString());
        }
       /* db.execSQL("INSERT INTO mobile_page_type (PageTypeId, PageTypeDesc" +
                ", Active) VALUES (1, 'Custom', 1);");

        db.execSQL("INSERT INTO mobile_pages" +
                "(PageId, PageTitle, Status, PageTypeId,Active) VALUES (1, 'Page 1', 1, 1,1);");
        db.execSQL("INSERT INTO mobile_pages" +
                "(PageId, PageTitle, Status, PageTypeId,Active) VALUES (2, 'Page 2', 1, 1,1);");
        db.execSQL("INSERT INTO mobile_pages" +
                "(PageId, PageTitle, Status, PageTypeId,Active) VALUES (3, 'Page 3', 1, 1,1);");
        db.execSQL("INSERT INTO widget (WidgetId, WidgetTitle, Position," +
                " WidgetData, PageId) VALUES (1, 'Link', 5, " +
                "'{\\\"link\\\":\\\"http://192.168.1.6/tradenowwebapp/\\\",\\\"caption\\\":\\\"Home\\\"}', '1');");
        db.execSQL("INSERT INTO widget (WidgetId, WidgetTitle, Position, " +
                "WidgetData, PageId) VALUES (2, 'Image', 2, '{\\\"url\\\":\\\"http://192.168.1.6/tradenowwebapp/img/logo.png\\\"}'," +
                " 1);");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteAllData() {
        deleteSignal();
    }

    public void getDatabaseStructure() {
        final ArrayList<String> dirArray = new ArrayList<String>();

        SQLiteDatabase DB = getReadableDatabase();
        //SQLiteDatabase DB = sqlHelper.getWritableDatabase();
        Cursor c = DB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        c.moveToFirst();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                dirArray.add(c.getString(c.getColumnIndex("name")));

                c.moveToNext();
            }
        }
        Log.i(TAG, "##" + dirArray);
        c.close();

    }

    public boolean insertSignal(List<ResponseSignalDTO> signalDTOList) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        DateUtils dateUtils = new DateUtils();
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                for (ResponseSignalDTO signalDTO : signalDTOList) {
                    contentValues.put(SqlContract.SqlSignal.TICKET, signalDTO.getTicket());
                    contentValues.put(SqlContract.SqlSignal.SYMBOL, signalDTO.getSymbol());
                    contentValues.put(SqlContract.SqlSignal.S_TYPE, signalDTO.getsType());
                    contentValues.put(SqlContract.SqlSignal.LOT, signalDTO.getLot());
                    contentValues.put(SqlContract.SqlSignal.PRICE, signalDTO.getPrice());
                    contentValues.put(SqlContract.SqlSignal.SL, signalDTO.getSl());
                    contentValues.put(SqlContract.SqlSignal.TP, signalDTO.getTp());
                    contentValues.put(SqlContract.SqlSignal.CLOSE_PRICE, signalDTO.getClosePrice());
                    contentValues.put(SqlContract.SqlSignal.SWAP, signalDTO.getSwap());
                    contentValues.put(SqlContract.SqlSignal.PROFIT, signalDTO.getProfit());
                    contentValues.put(SqlContract.SqlSignal.OPEN_TIME,
                            dateUtils.getDateAndTimeFromLong(signalDTO.getOpenTime()));
                    contentValues.put(SqlContract.SqlSignal.CLOSE_TIME,
                            dateUtils.getDateAndTimeFromLong(signalDTO.getCloseTime()));
                    contentValues.put(SqlContract.SqlSignal.STATUS, signalDTO.getStatus());
                    contentValues.put(SqlContract.SqlSignal.COPY, signalDTO.getCopy());
                    contentValues.put(SqlContract.SqlSignal.EXP_TIME,
                            dateUtils.getDateAndTimeFromLong(signalDTO.getExpTime()));
                    contentValues.put(SqlContract.SqlSignal.SIGNAL_DATE,
                            dateUtils.getReadableDateFromLong(signalDTO.getOpenTime()));
                    if (!sqLiteDatabase.isOpen()) sqLiteDatabase = getWritableDatabase();
                    count = sqLiteDatabase.insert(SqlContract.SqlSignal.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Signal is Added Successfully");
                    flagError = true;
                }
            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert Signal " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##Insert User" + errorMessage);
        }
        return flagError;
    }


    public boolean insertTradeBackUp(List<ResponseTradeBackUp> tradebackupList) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        DateUtils dateUtils = new DateUtils();
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                for (ResponseTradeBackUp tradeBackUp : tradebackupList) {
                    contentValues.put(SqlContract.SqlTradeBackUp.MASTER_ACC_ID, tradeBackUp.getMasterAccountId());
                    contentValues.put(SqlContract.SqlTradeBackUp.TICKET, tradeBackUp.getTicket());
                    contentValues.put(SqlContract.SqlTradeBackUp.SYMBOL, tradeBackUp.getSymbol());
                    contentValues.put(SqlContract.SqlTradeBackUp.S_TYPE, tradeBackUp.getsType());
                    contentValues.put(SqlContract.SqlTradeBackUp.LOT, tradeBackUp.getLot());
                    contentValues.put(SqlContract.SqlTradeBackUp.PRICE, tradeBackUp.getPrice());
                    contentValues.put(SqlContract.SqlTradeBackUp.SL, tradeBackUp.getSl());
                    contentValues.put(SqlContract.SqlTradeBackUp.TP, tradeBackUp.getTp());
                    contentValues.put(SqlContract.SqlTradeBackUp.CLOSE_PRICE, tradeBackUp.getClosePrice());
                    contentValues.put(SqlContract.SqlTradeBackUp.SWAP, tradeBackUp.getSwap());
                    contentValues.put(SqlContract.SqlTradeBackUp.PROFIT, tradeBackUp.getProfit());
                    contentValues.put(SqlContract.SqlTradeBackUp.OPEN_TIME,
                            dateUtils.getDateAndTimeFromLong(tradeBackUp.getOpenTime()));
                    contentValues.put(SqlContract.SqlTradeBackUp.CLOSE_TIME,
                            dateUtils.getDateAndTimeFromLong(tradeBackUp.getCloseTime()));
                    contentValues.put(SqlContract.SqlTradeBackUp.STATUS, tradeBackUp.getStatus());
                    contentValues.put(SqlContract.SqlTradeBackUp.TRADE_BACK_DATE,
                            dateUtils.getReadableDateFromLong(tradeBackUp.getCloseTime()));
                    contentValues.put(SqlContract.SqlTradeBackUp.PL_IN_PIPS, tradeBackUp.getPips());
                    if (!sqLiteDatabase.isOpen())
                        sqLiteDatabase = getWritableDatabase();
                    count = sqLiteDatabase.insert(SqlContract.SqlTradeBackUp.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Trade backup is Added Successfully");
                    flagError = true;
                }
            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert Trade backup " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##Insert Trade backup" + errorMessage);
        }
        return flagError;
    }

    public ArrayList<SignalDateDTO> getSignalDateList(String status) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<SignalDateDTO> signalDateList = null;
        try {
            String[] whereClause = new String[]{status};
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT Distinct " + SqlContract.SqlSignal.
                        SIGNAL_DATE + " From " + SqlContract.SqlSignal.TABLE_NAME + " where " +
                        SqlContract.SqlSignal.STATUS + "=?", whereClause);
                signalDateList = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        do {
                            //Log.i(TAG, "##" + cursor.getCount() + " " + cursor.getInt(1));
                            String signalDate = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.SIGNAL_DATE));
                            SignalDateDTO signalDateDTO = new SignalDateDTO(signalDate);
                            signalDateList.add(signalDateDTO);

                        } while (cursor.moveToNext());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }

        return signalDateList;
    }

    public ArrayList<SignalDataDTO> getSignalDataList(String status, String openTime) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<SignalDataDTO> signalDataList = null;
        try {
            String[] whereClause = new String[]{status, openTime};
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT * From " + SqlContract.SqlSignal.TABLE_NAME + " where " +
                        SqlContract.SqlSignal.STATUS + "=? AND " + SqlContract.SqlSignal.SIGNAL_DATE + "=?", whereClause);
                signalDataList = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        do {
                            long ticket = cursor.getLong(cursor.getColumnIndex(SqlContract.SqlSignal.TICKET));
                            String symbol = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.SYMBOL));
                            int sType = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlSignal.S_TYPE));
                            double lot = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.LOT));
                            double price = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.PRICE));
                            double sl = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.SL));
                            double tp = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.TP));
                            double closePrice = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.CLOSE_PRICE));
                            double swap = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.SWAP));
                            double profit = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.PROFIT));
                            String openTimeData = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.OPEN_TIME));
                            String closeTime = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.CLOSE_TIME));
                            String statusData = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.STATUS));
                            int copy = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlSignal.COPY));
                            String expTime = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.EXP_TIME));

                            SignalDataDTO signalDataDTO = new SignalDataDTO(ticket, symbol, sType, lot, price, sl, tp, closePrice, swap, profit, openTimeData, closeTime, statusData
                                    , copy, expTime);
                            signalDataList.add(signalDataDTO);

                        } while (cursor.moveToNext());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return signalDataList;
    }

    public SignalDataDTO getSignalData(long ticketNo) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        SignalDataDTO signalDataDTO = null;
        try {
            String[] whereClause = new String[]{String.valueOf(ticketNo)};
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT * From " + SqlContract.SqlSignal.TABLE_NAME + " where " +
                        SqlContract.SqlSignal.TICKET + "=?", whereClause);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        int ticket = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlSignal.TICKET));
                        String symbol = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.SYMBOL));
                        int sType = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlSignal.S_TYPE));
                        double lot = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.LOT));
                        double price = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.PRICE));
                        double sl = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.SL));
                        double tp = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.TP));
                        double closePrice = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.CLOSE_PRICE));
                        double swap = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.SWAP));
                        double profit = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlSignal.PROFIT));
                        String openTimeData = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.OPEN_TIME));
                        String closeTime = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.CLOSE_TIME));
                        String statusData = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.STATUS));
                        int copy = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlSignal.COPY));
                        String expTime = cursor.getString(cursor.getColumnIndex(SqlContract.SqlSignal.EXP_TIME));
                        signalDataDTO = new SignalDataDTO(ticket, symbol, sType, lot, price, sl, tp, closePrice, swap, profit, openTimeData, closeTime, statusData
                                , copy, expTime);
                    }
                } else {
                    signalDataDTO = new SignalDataDTO();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return signalDataDTO;
    }

    public ArrayList<TradeBackupDateDTO> getTradeDateList() {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<TradeBackupDateDTO> tradeBackupDateList = null;
        try {
            //String[] whereClause = new String[]{status};
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT Distinct " + SqlContract.SqlTradeBackUp.
                        TRADE_BACK_DATE + " From " + SqlContract.SqlTradeBackUp.TABLE_NAME, null);
                tradeBackupDateList = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        do {
                            //Log.i(TAG, "##" + cursor.getCount() + " " + cursor.getInt(1));
                            String tradeDate = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.
                                    TRADE_BACK_DATE));
                            TradeBackupDateDTO backupDateDTO = new TradeBackupDateDTO(tradeDate);
                            tradeBackupDateList.add(backupDateDTO);

                        } while (cursor.moveToNext());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }

        return tradeBackupDateList;
    }


    public ArrayList<TradeBackupDataDTO> getTradeBackupDataList(String openTime) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<TradeBackupDataDTO> backupDataDTOs = null;
        try {
            String[] whereClause = new String[]{openTime};
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT * From " + SqlContract.SqlTradeBackUp.TABLE_NAME + " where "
                        + SqlContract.SqlTradeBackUp.TRADE_BACK_DATE + "=?", whereClause);
                backupDataDTOs = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        do {
                            long masterAccountNo = cursor.getLong(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.MASTER_ACC_ID));
                            long ticket = cursor.getLong(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.TICKET));
                            String symbol = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.SYMBOL));
                            int sType = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.S_TYPE));
                            double lot = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.LOT));
                            double price = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.PRICE));
                            double sl = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.SL));
                            double tp = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.TP));
                            double closePrice = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.CLOSE_PRICE));
                            double swap = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.SWAP));
                            double profit = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.PROFIT));
                            String openTimeData = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.OPEN_TIME));
                            String closeTime = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.CLOSE_TIME));
                            String statusData = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.STATUS));
                            double pips = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.PL_IN_PIPS));

                            TradeBackupDataDTO tradeBackupDataDTO = new TradeBackupDataDTO(masterAccountNo, ticket, symbol, sType, lot, price, sl, tp, closePrice, swap, profit, openTimeData, closeTime, statusData
                                    , pips);
                            backupDataDTOs.add(tradeBackupDataDTO);

                        } while (cursor.moveToNext());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return backupDataDTOs;
    }

    public TradeBackupDataDTO getTradeBackUp(long ticketNo) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        TradeBackupDataDTO tradeBackupDTO = null;
        try {
            String[] whereClause = new String[]{String.valueOf(ticketNo)};
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT * From " + SqlContract.SqlTradeBackUp.TABLE_NAME + " where " +
                        SqlContract.SqlTradeBackUp.TICKET + "=?", whereClause);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        long masterId = cursor.getLong(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.MASTER_ACC_ID));
                        int ticket = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.TICKET));
                        String symbol = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.SYMBOL));
                        int sType = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.S_TYPE));
                        double lot = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.LOT));
                        double price = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.PRICE));
                        double sl = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.SL));
                        double tp = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.TP));
                        double closePrice = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.CLOSE_PRICE));
                        double swap = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.SWAP));
                        double profit = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.PROFIT));
                        String openTimeData = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.OPEN_TIME));
                        String closeTime = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.CLOSE_TIME));
                        String statusData = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.STATUS));
                        double pips = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTradeBackUp.PL_IN_PIPS));
                        tradeBackupDTO = new TradeBackupDataDTO(masterId, ticket, symbol, sType, lot, price, sl, tp, closePrice, swap, profit, openTimeData, closeTime, statusData
                                , pips);
                    }
                } else {
                    tradeBackupDTO = new TradeBackupDataDTO();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return tradeBackupDTO;
    }

    public boolean deleteSignal() {
        SQLiteDatabase sqLiteDatabase = null;
        sqLiteDatabase = getWritableDatabase();
        long count = -1;

        try {
            synchronized (sqLiteDatabase) {
                count = sqLiteDatabase.delete(SqlContract.SqlSignal.TABLE_NAME, null, null);
                Log.d(TAG, " ## delete Signal data successfully");
            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.d(TAG, "## Error to delete Signal data" + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return count != -1;
    }


    /**
     * Page insert
     */
    public boolean insertPageTypes(List<ResponsePageType> pagesList) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                for (ResponsePageType pageType : pagesList) {
                    contentValues.put(SqlContract.SqlPageType.PAGE_TYPE_ID, pageType.getPageTypeId());
                    contentValues.put(SqlContract.SqlPageType.PAGET_TYPE_DESC, pageType.getPageTypeDesc());
                    contentValues.put(SqlContract.SqlPageType.ACTIVE, pageType.getActive());
                    if (!sqLiteDatabase.isOpen()) sqLiteDatabase = getWritableDatabase();
                    count = sqLiteDatabase.insert(SqlContract.SqlPageType.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Page type is Added Successfully");
                    flagError = true;
                }
            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert Page type " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##Insert Page type" + errorMessage);
        }
        return flagError;
    }

    public boolean insertPages(List<ResponsePageData> pagesList) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        DateUtils dateUtils = new DateUtils();
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                for (ResponsePageData pageData : pagesList) {
                    contentValues.put(SqlContract.SqlPage.PAGE_ID, pageData.getPageId());
                    contentValues.put(SqlContract.SqlPage.PAGE_TITLE, pageData.getPageTitle());
                    contentValues.put(SqlContract.SqlPage.PAGE_STATUS, pageData.getStatus());
                    contentValues.put(SqlContract.SqlPage.PAGE_TYPE_ID, pageData.getPageType());
                    contentValues.put(SqlContract.SqlPage.ACTIVE, pageData.getActive());
                    if (!sqLiteDatabase.isOpen()) sqLiteDatabase = getWritableDatabase();
                    count = sqLiteDatabase.insert(SqlContract.SqlPage.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Page is Added Successfully");
                    flagError = true;
                }
            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert Page " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##Insert Page" + errorMessage);
        }
        return flagError;
    }

    public boolean insertWidgets(List<ResponseWidgetData> widgetsList) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        DateUtils dateUtils = new DateUtils();
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                for (ResponseWidgetData widgetData : widgetsList) {
                    contentValues.put(SqlContract.SqlWidget.WIDGET_ID, widgetData.getWidgetId());
                    contentValues.put(SqlContract.SqlWidget.WIDGET_TITLE, widgetData.getWidgetTitle());
                    contentValues.put(SqlContract.SqlWidget.POSITION, widgetData.getPosition());
                    contentValues.put(SqlContract.SqlWidget.WIDGET_DATA, widgetData.getWidgetData());
                    contentValues.put(SqlContract.SqlWidget.ACTIVE, widgetData.getActive());
                    if (!sqLiteDatabase.isOpen()) sqLiteDatabase = getWritableDatabase();
                    count = sqLiteDatabase.insert(SqlContract.SqlWidget.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## widget is Added Successfully");
                    flagError = true;
                }
            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert widget " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##Insert widget" + errorMessage);
        }
        return flagError;
    }

    public boolean updatePages(List<ResponsePageData> updatePageList) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        DateUtils dateUtils = new DateUtils();
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                for (ResponsePageData pageData : updatePageList) {
                    String[] whereClause = new String[]{String.valueOf(pageData.getPageId())};
                    String strPageTitle = pageData.getPageTitle();
                    int pageStatus = pageData.getStatus();
                    int pageTypeId = pageData.getPageType();
                    int active = pageData.getActive();

                    if (!TextUtils.isEmpty(strPageTitle) || strPageTitle != null)
                        contentValues.put(SqlContract.SqlPage.PAGE_TITLE, strPageTitle);
                    if (pageStatus != 0)
                        contentValues.put(SqlContract.SqlPage.PAGE_STATUS, pageStatus);
                    if (pageTypeId != 0)
                        contentValues.put(SqlContract.SqlPage.PAGE_TYPE_ID, pageTypeId);
                    contentValues.put(SqlContract.SqlPage.ACTIVE, pageData.getActive());
                    if (!sqLiteDatabase.isOpen()) sqLiteDatabase = getWritableDatabase();
                    count = sqLiteDatabase.update(SqlContract.SqlPage.TABLE_NAME, contentValues,
                            SqlContract.SqlPage.PAGE_ID + "=?", whereClause);
                    contentValues.clear();
                    Log.d(TAG, "## Page is Updated Successfully");
                    flagError = true;
                }
            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while Update Page " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##update Page" + errorMessage);
        }
        return flagError;
    }

    public boolean updateWidgets(List<ResponseWidgetData> widgetsList) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        DateUtils dateUtils = new DateUtils();
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                for (ResponseWidgetData widgetData : widgetsList) {
                    String strWidgetTitle = widgetData.getWidgetTitle();
                    String strWidgetData = widgetData.getWidgetData();
                    int position = widgetData.getPosition();
                    int active = widgetData.getActive();
                    if (TextUtils.isEmpty(strWidgetTitle) || strWidgetTitle != null)
                        contentValues.put(SqlContract.SqlWidget.WIDGET_TITLE, strWidgetTitle);
                    if (TextUtils.isEmpty(strWidgetData) || strWidgetData != null)
                        contentValues.put(SqlContract.SqlWidget.WIDGET_DATA, strWidgetData);
                    if (position != 0)
                        contentValues.put(SqlContract.SqlWidget.POSITION, position);
                    contentValues.put(SqlContract.SqlWidget.ACTIVE, active);
                    if (!sqLiteDatabase.isOpen()) sqLiteDatabase = getWritableDatabase();
                    count = sqLiteDatabase.insert(SqlContract.SqlWidget.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## widget is updated Successfully");
                    flagError = true;
                }
            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while update widget " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##update widget" + errorMessage);
        }
        return flagError;
    }

    public ArrayList<String> getMobilePageList() {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<String> mobilePageList = null;
        try {
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT " + SqlContract.SqlPage.
                        PAGE_TITLE + " From " + SqlContract.SqlPage.TABLE_NAME + " where " +
                        SqlContract.SqlPage.ACTIVE + "=1", null);
                mobilePageList = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            //Log.i(TAG, "##" + cursor.getCount() + " " + cursor.getInt(1));
                            String pageTitle = cursor.getString(cursor.getColumnIndex(SqlContract.SqlPage.PAGE_TITLE));
                            mobilePageList.add(pageTitle);

                        } while (cursor.moveToNext());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }

        return mobilePageList;
    }
}
