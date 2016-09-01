package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.History_Model;

/**
 * Created by stepsecret on 17/8/2559.
 */
public interface History_API {

    @GET("/array/api/v1/history")
    public void Get_History_API(@Query("api_key") String api_key, Callback<History_Model> response);

}
