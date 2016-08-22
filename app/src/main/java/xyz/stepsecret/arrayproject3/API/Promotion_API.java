package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.Promotion_Model;

/**
 * Created by stepsecret on 17/8/2559.
 */
public interface Promotion_API {

    @GET("/array/v1/promotion")
    public void Get_Promotion_API(@Query("api_key") String api_key, @Query("latitude") String latitude, @Query("longitude") String longitude,  Callback<Promotion_Model> response);

}
