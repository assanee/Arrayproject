package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.Near_Model;

/**
 * Created by stepsecret on 22/8/2559.
 */
public interface Near_API {

    @GET("/array/api/v1/near")
    public void Get_NEAR_API(@Query("api_key") String api_key, @Query("latitude") String latitude, @Query("longitude") String longitude, Callback<Near_Model> response);

}
