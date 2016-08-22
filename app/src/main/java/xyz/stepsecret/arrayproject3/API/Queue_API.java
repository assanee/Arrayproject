package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.Queue_Model;

/**
 * Created by stepsecret on 20/8/2559.
 */
public interface Queue_API {

    @GET("/array/v1/queue")
    public void Get_Queue_API(@Query("api_key") String api_key, Callback<Queue_Model> response);

}
