package com.vibeosys.tradenow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vibeosys.tradenow.data.adapterdata.SignalDataDTO;
import com.vibeosys.tradenow.data.adapterdata.SignalDateDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseSignalDTO;
import com.vibeosys.tradenow.utils.DateUtils;
import com.vibeosys.tradenow.utils.SessionManager;

import java.sql.Date;
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
            ")";

    public DbRepository(Context context, SessionManager sessionManager) {
        super(context, DATABASE_NAME, null, sessionManager.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SIGNAL);
        Log.d(TAG, "Signal Table Create" + CREATE_SIGNAL);
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
}
