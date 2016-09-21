package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by stepsecret on 01-12-2015.
 */
public class ProSingleItemModel {


    private String name;
    private String url;
    private String description;
    private String id;


    public ProSingleItemModel() {
    }

    public ProSingleItemModel(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
