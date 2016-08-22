package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.Favorite_Model;

/**
 * Created by stepsecret on 16/8/2559.
 */
public interface Favorite_API {

    @GET("/array/v1/favorite")
    public void Get_Favorite_API(@Query("api_key") String api_key, Callback<Favorite_Model> response);

}
