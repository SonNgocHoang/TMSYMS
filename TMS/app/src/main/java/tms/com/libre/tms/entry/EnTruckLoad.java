package tms.com.libre.tms.entry;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quangnv on 4/11/17.
 */

public class EnTruckLoad {

    @SerializedName("StatusCode")
    private int StatusCode;
    @SerializedName("Content")
    private ArrayList<Content> Content;

    public int getStatusCode() {
        return StatusCode;
    }

    public ArrayList<Content> getContent() {
        return Content;
    }

    public static class Content {
        @SerializedName("ID")
        private int ID;
        @SerializedName("TLNO")
        private String TLNO;
        @SerializedName("TruckNo")
        private String TruckNo;
        @SerializedName("DriverID")
        private int DriverID;
        @SerializedName("DT")
        private String DT;
        @SerializedName("Driver")
        private String Driver;
        @SerializedName("Status")
        private int Status;
        @SerializedName("TotalCount")
        private int TotalCount;
        @SerializedName("VehicleCount")
        private int VehicleCount;

        public int getID() {
            return ID;
        }

        public String getTLNO() {
            return TLNO;
        }

        public String getTruckNo() {
            return TruckNo;
        }

        public int getDriverID() {
            return DriverID;
        }

        public String getDT() {
            return DT;
        }

        public String getDriver() {
            return Driver;
        }

        public int getStatus() {
            return Status;
        }

        public int getTotalCount() {
            return TotalCount;
        }

        public int getVehicleCount() {
            return VehicleCount;
        }
    }
}
