package tms.com.libre.tms.entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GL62 on 4/14/2017.
 */

public class EnDamageTypeResponse {

    @SerializedName("StatusCode")
    private int statuscode;
    @SerializedName("Content")
    private List<Content> content;

    public int getStatuscode() {
        return statuscode;
    }

    public List<Content> getContent() {
        return content;
    }

    public static class Content {
        @SerializedName("ID")
        private int id;
        @SerializedName("Name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
