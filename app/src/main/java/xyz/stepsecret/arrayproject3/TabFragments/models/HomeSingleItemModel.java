package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class HomeSingleItemModel {


    private String name;
    private String url;
    private String description;
    private String id;


    public HomeSingleItemModel() {
    }

    public HomeSingleItemModel(String id, String name, String url) {
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
