package tms.com.libre.tms.entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by quangnv on 4/11/17.
 */

public class EnValidateResponse {


    @SerializedName("StatusCode")
    private int StatusCode;
    @SerializedName("Content")
    private String Content;

    public int getStatusCode() {
        return StatusCode;
    }

    public String getContent() {
        return Content;
    }
}