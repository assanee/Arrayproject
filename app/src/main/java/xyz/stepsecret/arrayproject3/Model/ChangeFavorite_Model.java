package xyz.stepsecret.arrayproject3.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stepsecret on 1/9/2559.
 */
public class ChangeFavorite_Model {


    @SerializedName("message")
    private String message ;

    @SerializedName("error")
    private Boolean error ;

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }


}
