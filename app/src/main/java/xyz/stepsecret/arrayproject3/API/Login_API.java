package xyz.stepsecret.arrayproject3.API;

/**
 * Created by Assanee on 8/7/2558.
 */
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

import retrofit.Callback;
import xyz.stepsecret.arrayproject3.Model.Login_Model;


public interface Login_API {

    @FormUrlEncoded
    @POST("/array/v1/login")
    public void SignIN(@Field("email") String email, @Field("password") String password, Callback<Login_Model> response);


}
