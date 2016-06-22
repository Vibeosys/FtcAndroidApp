package com.vibeosys.tradenow.data.requestdata;

import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 22-06-2016.
 */
public class GetSignalRequest extends BaseDTO {

    private long date;

    public GetSignalRequest(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
