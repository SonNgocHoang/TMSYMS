package tms.com.libre.tms.entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by quangnv on 4/11/17.
 */

public class EnLoginResponse {


    @SerializedName("StatusCode")
    private int StatusCode;
    @SerializedName("Content")
    private Content Content;

    public int getStatusCode() {
        return StatusCode;
    }

    public Content getContent() {
        return Content;
    }

    public static class Content {
        @SerializedName("token")
        private String token;

        public String getToken() {
            return token;
        }
    }
}
