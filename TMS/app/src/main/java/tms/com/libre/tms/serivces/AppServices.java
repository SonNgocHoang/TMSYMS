package tms.com.libre.tms.serivces;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import tms.com.libre.tms.entry.EnDamageLoadListResponse;
import tms.com.libre.tms.entry.EnDamageCodeResponse;
import tms.com.libre.tms.entry.EnDamageSeverityResponse;
import tms.com.libre.tms.entry.EnDamageTypeResponse;
import tms.com.libre.tms.entry.EnLoginResponse;
import tms.com.libre.tms.entry.EnTrucLoadStatusListResponse;
import tms.com.libre.tms.entry.EnTruckLoadResponse;
import tms.com.libre.tms.entry.EnValidateResponse;

/**
 * Created by quangnv on 4/11/17.
 */

public interface AppServices {

    @Multipart
    @POST("/Account/GetValidationToken")
    public void login(@Part("email") String email, @Part("password") String password, Callback<EnLoginResponse> loginResponseCallback);

    @GET("/Account/ValidateToken")
    public void checkValidationToken(@Query("token") String token, Callback<EnValidateResponse> responseCallback);

    @GET("/MobileApi/getTruckLoad")
    public void getTruckLoad(@Query("tokenId") String tokenId,
                             @Query("driverId") String driverId,
                             @Query("statusId") String statusId,
                             @Query("pagesize") String pageSize,
                             Callback<EnTruckLoadResponse> truckloadResponse
    );

    @GET("/MobileApi/getDamageLoadList")
    public void getDamageLoadList(@Query("tokenId") String tokenId,
                                  @Query("pageno") String pageno,
                                  @Query("pagesize") String pagesize,
                                  Callback<EnDamageLoadListResponse> enDamageLoadListResponseCallback
    );

    @GET("/MobileApi/getDamageType")
    public void getDamageType(Callback<EnDamageTypeResponse> enDamageTypeResponseCallback);

    @GET("/MobileApi/getTruckLoadStatusList")
    public void getTruckLoadStatusList(Callback<EnTrucLoadStatusListResponse> enTrucLoadStatusListCallback);

    @GET("/MobileApi/getDamageSeverity")
    public void getDamageSeverity(Callback<EnDamageSeverityResponse> enDamageSeverityResponseCallback);

    @GET("/MobileApi/getDamageCode")
    public void getDamageCode(Callback<EnDamageCodeResponse> enDamageCodeResponseCallback);
}
