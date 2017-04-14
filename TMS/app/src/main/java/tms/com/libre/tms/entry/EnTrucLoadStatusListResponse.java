package tms.com.libre.tms.entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GL62 on 4/14/2017.
 */

public class EnTrucLoadStatusListResponse {

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
        @SerializedName("Scheduled")
        private int Scheduled;
        @SerializedName("Pickup")
        private int Pickup;
        @SerializedName("OnRoute")
        private int OnRoute;
        @SerializedName("Discharge")
        private int Discharge;
        @SerializedName("Completed")
        private int Completed;

        public int getScheduled() {
            return Scheduled;
        }

        public int getPickup() {
            return Pickup;
        }

        public int getOnRoute() {
            return OnRoute;
        }

        public int getDischarge() {
            return Discharge;
        }

        public int getCompleted() {
            return Completed;
        }
    }
}
