package com.vibeosys.tradenow.data.responsedata;

import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 22-06-2016.
 */
public class BaseResponseDTO extends BaseDTO {

    private String error;
    private String data;

    public BaseResponseDTO() {
    }

    public BaseResponseDTO(String error, String data) {
        this.error = error;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
