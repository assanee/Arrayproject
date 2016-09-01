package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.Branch_Model;

/**
 * Created by stepsecret on 21/8/2559.
 */
public interface Branch_API {

    @GET("/array/api/v1/branch")
    public void Get_Branch_API(@Query("api_key") String api_key, @Query("id_branch") String id_branch, Callback<Branch_Model> response);

}
