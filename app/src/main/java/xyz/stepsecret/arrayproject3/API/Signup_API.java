package xyz.stepsecret.arrayproject3.API;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import xyz.stepsecret.arrayproject3.Model.Signup_Model;

/**
 * Created by Assanee on 8/7/2558.
 */
public interface Signup_API {

    @FormUrlEncoded
    @POST("/Array/v1/register")
    public void SignUP(@Field("username") String name, @Field("first_name") String first_name,
                       @Field("last_name") String last_name, @Field("birthday") String birthday,
                       @Field("gender") String gender, @Field("email") String email,
                       @Field("password") String password, @Field("repassword") String repassword,
                       @Field("class") String user, Callback<Signup_Model> response);

}
