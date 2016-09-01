package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import xyz.stepsecret.arrayproject3.Model.ChangeFavorite_Model;

/**
 * Created by stepsecret on 1/9/2559.
 */
public interface ChangeFavorite_API {

    @FormUrlEncoded
    @POST("/array/api/v1/favorite")
    public void ChangeFavorite_API(@Field("api_key") String api_key, @Field("id_branch") String id_branch, Callback<ChangeFavorite_Model> response);

}
