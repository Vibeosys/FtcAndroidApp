package com.vibeosys.tradenow.data.responsedata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

/**
 * Created by akshay on 28-06-2016.
 */
public class ResponseTradeBackUp extends BaseResponseDTO {

    private long masterAccountId;
    private long ticket;
    private String symbol;
    private int sType;
    private double lot;
    private double price;
    private double sl;
    private double tp;
    private double closePrice;
    private double swap;
    private double profit;
    private long openTime;
    private long closeTime;
    private String status;
    private double plPips;

    public ResponseTradeBackUp() {
    }

    public long getMasterAccountId() {
        return masterAccountId;
    }

    public void setMasterAccountId(long masterAccountId) {
        this.masterAccountId = masterAccountId;
    }

    public long getTicket() {
        return ticket;
    }

    public void setTicket(long ticket) {
        this.ticket = ticket;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getsType() {
        return sType;
    }

    public void setsType(int sType) {
        this.sType = sType;
    }

    public double getLot() {
        return lot;
    }

    public void setLot(double lot) {
        this.lot = lot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSl() {
        return sl;
    }

    public void setSl(double sl) {
        this.sl = sl;
    }

    public double getTp() {
        return tp;
    }

    public void setTp(double tp) {
        this.tp = tp;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getSwap() {
        return swap;
    }

    public void setSwap(double swap) {
        this.swap = swap;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPips() {
        return plPips;
    }

    public void setPips(double pips) {
        this.plPips = pips;
    }

    public static ArrayList<ResponseTradeBackUp> deserializeToArray(String serializedString) {
        Gson gson = new Gson();
        ArrayList<ResponseTradeBackUp> tradeBackupDTOs = null;
        try {
            ResponseTradeBackUp[] deserializeObject = gson.fromJson(serializedString, ResponseTradeBackUp[].class);
            tradeBackupDTOs = new ArrayList<>();
            for (ResponseTradeBackUp tradeBackupDTO : deserializeObject) {
                tradeBackupDTOs.add(tradeBackupDTO);
            }
        } catch (JsonSyntaxException e) {
            Log.e("deserialize", "Response Response trade backup DTO error" + e.toString());
        }


        return tradeBackupDTOs;
    }
}
