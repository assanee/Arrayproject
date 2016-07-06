package xyz.stepsecret.arrayproject3.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Assanee on 8/7/2558.
 */
public class Login_Model {

    @SerializedName("error")
    private Boolean error ;

    @SerializedName("username")
    private String username;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("email")
    private String email;

    @SerializedName("apiKey")
    private String apiKey;

    @SerializedName("class")
    private String class_data;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("message")
        private String message;


    public Boolean getError() {
        return error;
    }

    public String getUsername() {
        return username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getClass_data() {
        return class_data;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
            return message;
        }


}
