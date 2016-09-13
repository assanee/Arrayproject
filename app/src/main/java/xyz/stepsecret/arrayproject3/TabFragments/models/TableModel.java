package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by stepsecret on 15/01/16.
 */
public class TableModel {
    private String id_table, wait_queue, table, wait_time;

    public TableModel() {
    }

    public TableModel(String id_table, String wait_queue, String table, String wait_time) {

        this.id_table = id_table;
        this.wait_queue = wait_queue;
        this.table = table;
        this.wait_time = wait_time;

    }

    public String getIdtable() {
        return id_table;
    }

    public void setWaitqueue(String wait_queue) {
        this.wait_queue = wait_queue;
    }

    public String getWaitqueue() {
        return wait_queue;
    }

    public void setIdtable(String id_table) {
        this.id_table = id_table;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getWaittime() {
        return wait_time;
    }

    public void setWaittime(String wait_time) {
        this.wait_time = wait_time;
    }




}
