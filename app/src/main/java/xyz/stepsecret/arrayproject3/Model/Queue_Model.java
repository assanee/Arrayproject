package xyz.stepsecret.arrayproject3.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stepsecret on 20/8/2559.
 */
public class Queue_Model {

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private String[][] data;

    @SerializedName("status")
    private String[][] status;

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String[][] getData() {
        return data;
    }

    public String[][] getStatus() {
        return status;
    }

}
