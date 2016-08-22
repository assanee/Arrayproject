package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import xyz.stepsecret.arrayproject3.Model.Reserve_Model;

/**
 * Created by stepsecret on 21/8/2559.
 */
public interface Reserve_API {

    @FormUrlEncoded
    @POST("/array/v1/createqueue")
    public void Reserve_API(@Field("api_key") String api_key, @Field("id_branch") String id_branch,
                       @Field("table_type") String table_type, @Field("number") String number, Callback<Reserve_Model> response);

}
