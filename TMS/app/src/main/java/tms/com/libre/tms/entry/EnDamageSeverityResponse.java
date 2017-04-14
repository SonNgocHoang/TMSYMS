package tms.com.libre.tms.entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GL62 on 4/14/2017.
 */

public class EnDamageSeverityResponse {

    @SerializedName("StatusCode")
    private int StatusCode;
    @SerializedName("Content")
    private List<Content> Content;

    public int getStatusCode() {
        return StatusCode;
    }

    public List<Content> getContent() {
        return Content;
    }

    public static class Content {
        @SerializedName("ID")
        private int ID;
        @SerializedName("Name")
        private String Name;

        public int getID() {
            return ID;
        }

        public String getName() {
            return Name;
        }
    }
}
