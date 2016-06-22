package com.vibeosys.tradenow.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vibeosys.tradenow.utils.SessionManager;

import java.util.ArrayList;

/**
 * Created by akshay on 22-06-2016.
 */
public class DbRepository extends SQLiteOpenHelper {

    private static final String TAG = DbRepository.class.getSimpleName();
    private final String CREATE_SIGNAL = "CREATE TABLE IF NOT EXISTS signal (" +
            "  Ticket int(10) unsigned NOT NULL," +
            "  Symbol varchar(45) DEFAULT NULL," +
            "  sType int(10) unsigned DEFAULT NULL," +
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
            "  copy int(10) unsigned DEFAULT NULL," +
            "  exp_time datetime DEFAULT NULL," +
            "  PRIMARY KEY (Ticket)" +
            ")";

    public DbRepository(Context context, SessionManager sessionManager) {
        super(context, sessionManager.getDatabaseDeviceFullPath(), null, sessionManager.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SIGNAL);
        Log.d(TAG, "Signal Table Create" + CREATE_SIGNAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
}
