package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.SearchNear_Model;

/**
 * Created by stepsecret on 20/9/2559.
 */
public interface SearchNear_API {
    @GET("/array/api/v1/searchnear")
    public void Get_Search_API(@Query("api_key") String api_key, @Query("search") String search, @Query("latitude") String latitude, @Query("longitude") String longitude, Callback<SearchNear_Model> response);

}
