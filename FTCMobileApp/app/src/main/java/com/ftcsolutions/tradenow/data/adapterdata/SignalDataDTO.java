package com.ftcsolutions.tradenow.data.adapterdata;

/**
 * Created by akshay on 21-06-2016.
 */
public class SignalDataDTO {

    private long mTicket;
    private String mSymbol;
    private int mSType;
    private double mLot;
    private double mPrice;
    private double mSl;
    private double mTp;
    private double mClosePrice;
    private double mSwap;
    private double mProfit;
    private String mOpenTime;
    private String mCloseTime;
    private String mStatus;
    private int mCopy;
    private String mExpTime;

    public SignalDataDTO() {
    }

    public SignalDataDTO(long mTicket, String mSymbol, int mSType, double mLot, double mPrice,
                         double mSl, double mTp, double mClosePrice, double mSwap, double mProfit,
                         String mOpenTime, String mCloseTime, String mStatus, int mCopy,
                         String mExpTime) {
        this.mTicket = mTicket;
        this.mSymbol = mSymbol;
        this.mSType = mSType;
        this.mLot = mLot;
        this.mPrice = mPrice;
        this.mSl = mSl;
        this.mTp = mTp;
        this.mClosePrice = mClosePrice;
        this.mSwap = mSwap;
        this.mProfit = mProfit;
        this.mOpenTime = mOpenTime;
        this.mCloseTime = mCloseTime;
        this.mStatus = mStatus;
        this.mCopy = mCopy;
        this.mExpTime = mExpTime;
    }

    public long getTicket() {
        return mTicket;
    }

    public void setTicket(long mTicket) {
        this.mTicket = mTicket;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public int getSType() {
        return mSType;
    }

    public void setSType(int mSType) {
        this.mSType = mSType;
    }

    public double getLot() {
        return mLot;
    }

    public void setLot(double mLot) {
        this.mLot = mLot;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public double getSl() {
        return mSl;
    }

    public void setSl(double mSl) {
        this.mSl = mSl;
    }

    public double getTp() {
        return mTp;
    }

    public void setTp(double mTp) {
        this.mTp = mTp;
    }

    public double getClosePrice() {
        return mClosePrice;
    }

    public void setClosePrice(double mClosePrice) {
        this.mClosePrice = mClosePrice;
    }

    public double getSwap() {
        return mSwap;
    }

    public void setSwap(double mSwap) {
        this.mSwap = mSwap;
    }

    public double getProfit() {
        return mProfit;
    }

    public void setProfit(double mProfit) {
        this.mProfit = mProfit;
    }

    public String getOpenTime() {
        return mOpenTime;
    }

    public void setOpenTime(String mOpenTime) {
        this.mOpenTime = mOpenTime;
    }

    public String getCloseTime() {
        return mCloseTime;
    }

    public void setCloseTime(String mCloseTime) {
        this.mCloseTime = mCloseTime;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public int getCopy() {
        return mCopy;
    }

    public void setCopy(int mCopy) {
        this.mCopy = mCopy;
    }

    public String getExpTime() {
        return mExpTime;
    }

    public void setExpTime(String mExpTime) {
        this.mExpTime = mExpTime;
    }
}
