package com.vibeosys.tradenow.data.requestdata;

import com.vibeosys.tradenow.data.BaseDTO;

import java.util.UUID;

/**
 * Created by akshay on 02-07-2016.
 */
public class RequestPageSync extends BaseDTO {

    private String referenceId;

    public RequestPageSync(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
