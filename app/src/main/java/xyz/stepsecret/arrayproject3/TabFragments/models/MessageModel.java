package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by Lincoln on 15/01/16.
 */
public class MessageModel {
    private String  id, message, date, time;
    private Boolean status;

    public MessageModel() {
    }

    public MessageModel(String id, String message, String date, String time, Boolean status) {

        this.id = id;
        this.date = date;
        this.message = message;
        this.time = time;
        this.status = status;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
