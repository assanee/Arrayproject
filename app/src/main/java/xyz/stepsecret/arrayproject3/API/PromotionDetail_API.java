package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.arrayproject3.Model.PromotionDetail_Model;

/**
 * Created by stepsecret on 24/8/2559.
 */
public interface PromotionDetail_API {


    @GET("/array/api/v1/promotiondetail")
    public void Get_PromotionDetail_API(@Query("api_key") String api_key, @Query("id_promotion") String id_branch, Callback<PromotionDetail_Model> response);


}
