package com.ftcsolutions.tradenow.data.requestdata;

import com.ftcsolutions.tradenow.data.BaseDTO;

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
