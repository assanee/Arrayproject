package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.Table_Model;

/**
 * Created by stepsecret on 7/9/2559.
 */
public interface Table_API {

    @GET("/array/api/v1/tabledataByIdbranch")
    public void Get_Table_API(@Query("api_key") String api_key, @Query("id_branch") String id_branch, Callback<Table_Model> response);


}
