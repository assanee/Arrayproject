package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by Lincoln on 15/01/16.
 */
public class QueueModel {
    private String current_queue_table, namebrand, current_queue, current_queue_number, wait_time, firstname, lastname, date, time, queue, number, logo_brand;
    private String[] alert;
    public QueueModel() {
    }

    public QueueModel(String[] alert,String current_queue_table, String namebrand, String current_queue, String current_queue_number, String wait_time, String firstname, String lastname, String date, String time, String queue, String number, String logo_brand) {
        this.current_queue_table = current_queue_table;
        this.namebrand = namebrand;
        this.current_queue = current_queue;
        this.current_queue_number = current_queue_number;
        this.wait_time = wait_time;
        this.firstname = firstname;
        this.lastname = lastname;
        this.date = date;
        this.time = time;
        this.queue = queue;
        this.number = number;
        this.logo_brand = logo_brand;
        this.alert = alert;
    }

    public String getCurrent_queue_table() {
        return namebrand;
    }

    public void setCurrent_queue_table(String current_queue_table) {
        this.current_queue_table = current_queue_table;
    }

    public String getNamebrand() {
        return namebrand;
    }

    public void setNamebrand(String namebrand) {
        this.namebrand = namebrand;
    }

    public String getCurrent_queue() {
        return current_queue;
    }

    public void setCurrent_queue(String current_queue) {
        this.current_queue = current_queue;
    }

    public String getCurrent_queue_number() {
        return current_queue_number;
    }

    public void setCurrent_queue_number(String current_queue_number) {
        this.current_queue_number = current_queue_number;
    }

    public String getWait_time() {
        return wait_time;
    }

    public void setWait_time(String wait_time) {
        this.wait_time = wait_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String name) {
        this.date = name;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getLogoBrand() {
        return logo_brand;
    }

    public void setLogoBrand(String logo_brand) {
        this.logo_brand = logo_brand;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String[] getAlert() {
        return alert;
    }

    public void setAlert(String[] alert) {
        this.alert = alert;
    }


}
