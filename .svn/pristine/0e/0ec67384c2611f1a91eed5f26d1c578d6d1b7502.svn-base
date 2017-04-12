package tms.com.libre.tms.serivces;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import tms.com.libre.tms.entry.EnLoginResponse;
import tms.com.libre.tms.entry.EnValidateResponse;

/**
 * Created by quangnv on 4/11/17.
 */

public interface AppServices {

    @GET("/Account/GetValidationToken")
    public void login(@Query("email") String email, @Query("password") String password, Callback<EnLoginResponse> loginResponseCallback);

    @GET("/Account/ValidateToken")
    public void checkValidationToken(@Query("token") String token , Callback<EnValidateResponse> responseCallback);

    @GET("/MobileApi/getTruckLoad")
    public void getTruckLoad(@Query("tokenId") String tokenId,
                             @Query("driverId") String driverId,
                             @Query("statusId") String statusId,
                             @Query("pagesize") String pageSize
                             );
}
