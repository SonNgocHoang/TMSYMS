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
        @SerializedName("userRoleID")
        private String userRoleID;
        @SerializedName("userRoleType")
        private String userRoleType;

        public String getToken() {
            return token;
        }

        public String getUserRoleID() {
            return userRoleID;
        }

        public String getUserRoleType() {
            return userRoleType;
        }
    }
}
