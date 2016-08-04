package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by stepsecret on 1/8/2559.
 */
public class ProSubSingleItemModel {


    private String name;
    private String url;
    private String description;
    private String id;


    public ProSubSingleItemModel() {
    }

    public ProSubSingleItemModel(String id, String name, String url) {
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
