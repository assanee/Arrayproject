package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import xyz.stepsecret.arrayproject3.Model.CancelQueue_Model;

/**
 * Created by stepsecret on 20/8/2559.
 */
public interface CancelQueue_API {

    @FormUrlEncoded
    @POST("/array/api/v1/canclequeue")
    public void DeleteQueue_API(@Field("api_key") String api_key, @Field("id_queue") String id_queue, Callback<CancelQueue_Model> response);

}
