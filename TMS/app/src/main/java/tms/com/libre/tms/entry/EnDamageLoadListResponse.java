package tms.com.libre.tms.entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GL62 on 4/14/2017.
 */

public class EnDamageLoadListResponse {

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
        @SerializedName("VehicleID")
        private int VehicleID;
        @SerializedName("VIN")
        private String VIN;
        @SerializedName("Model")
        private String Model;
        @SerializedName("Make")
        private String Make;
        @SerializedName("Year")
        private String Year;
        @SerializedName("Shipper")
        private String Shipper;
        @SerializedName("VehicleCount")
        private int VehicleCount;
        @SerializedName("TotalCount")
        private int TotalCount;
        @SerializedName("DT")
        private String DT;

        public int getVehicleID() {
            return VehicleID;
        }

        public String getVIN() {
            return VIN;
        }

        public String getModel() {
            return Model;
        }

        public String getMake() {
            return Make;
        }

        public String getYear() {
            return Year;
        }

        public String getShipper() {
            return Shipper;
        }

        public int getVehicleCount() {
            return VehicleCount;
        }

        public int getTotalCount() {
            return TotalCount;
        }

        public String getDT() {
            return DT;
        }
    }
}
