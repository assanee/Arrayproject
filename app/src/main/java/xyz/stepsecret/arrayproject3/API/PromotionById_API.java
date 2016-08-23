package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.PromotionById_Model;

/**
 * Created by stepsecret on 23/8/2559.
 */
public interface PromotionById_API {

    @GET("/array/v1/promotionbyid")
    public void Get_PromotionById_API(@Query("api_key") String api_key, @Query("id_branch") String id_branch, Callback<PromotionById_Model> response);

}
