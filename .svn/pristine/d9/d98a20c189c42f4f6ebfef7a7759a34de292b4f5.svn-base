package tms.com.libre.tms.serivces;

/**
 * Created by quangnv on 4/11/17.
 */

public class AppApi extends BaseApi {
    private AppServices appServices;

    public AppServices services(){
        if(appServices == null) {
        appServices = restAdapter.create(AppServices.class);

        }
        return appServices;
    }
}
