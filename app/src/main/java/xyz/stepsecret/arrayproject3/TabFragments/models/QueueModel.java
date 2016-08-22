package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by Lincoln on 15/01/16.
 */
public class QueueModel {
    private String current_queue_table, name_brand, name_branch, current_queue, current_queue_number, wait_time, wait_queue, firstname, lastname, date, time, queue, number_book, logo_brand;
    private String[] alert;
    private String id_queue;
    public QueueModel() {
    }

    public QueueModel(String id_queue, String[] alert,String current_queue_table, String name_brand, String name_branch, String current_queue, String current_queue_number, String wait_time, String wait_queue, String firstname, String lastname, String date, String time, String queue, String number_book, String logo_brand) {
        this.id_queue = id_queue;
        this.current_queue_table = current_queue_table;
        this.name_brand = name_brand;
        this.name_branch = name_branch;
        this.current_queue = current_queue;
        this.current_queue_number = current_queue_number;
        this.wait_time = wait_time;
        this.wait_queue = wait_queue;
        this.firstname = firstname;
        this.lastname = lastname;
        this.date = date;
        this.time = time;
        this.queue = queue;
        this.number_book = number_book;
        this.logo_brand = logo_brand;
        this.alert = alert;
    }

    public String getId_queue() {
        return id_queue;
    }

    public void setId_queue(String id_queue) {
        this.id_queue = id_queue;
    }

    public String getCurrent_queue_table() {
        return current_queue_table;
    }

    public void setCurrent_queue_table(String current_queue_table) {
        this.current_queue_table = current_queue_table;
    }

    public String getNamebrand() {
        return name_brand;
    }

    public void setNamebrand(String name_brand) {
        this.name_brand = name_brand;
    }

    public String getNamebranch() {
        return name_branch;
    }

    public void setNamebranch(String name_branch) {
        this.name_branch = name_branch;
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

    public String getWait_queue() {
        return wait_queue;
    }

    public void setWait_queue(String wait_queue) {
        this.wait_queue = wait_queue;
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

    public String getNumberBook() {
        return number_book;
    }

    public void setNumberBook(String number_book) {
        this.number_book = number_book;
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
