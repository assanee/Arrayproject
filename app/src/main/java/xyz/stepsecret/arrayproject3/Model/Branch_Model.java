package xyz.stepsecret.arrayproject3.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stepsecret on 21/8/2559.
 */
public class Branch_Model {

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private String[][] data;

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String[][] getData() {
        return data;
    }


}
