package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class HomeSingleItemModel {


    private String name_brand;
    private String name_branch;
    private String url;
    private String description;
    private String id;


    public HomeSingleItemModel() {
    }

    public HomeSingleItemModel(String id, String name_brand, String name_branch, String url) {
        this.id = id;
        this.name_brand = name_brand;
        this.name_branch = name_branch;
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

    public String getNameBrand() {
        return name_brand;
    }

    public void setNameBrand(String name_brand) {
        this.name_brand = name_brand;
    }

    public String getNameBranch() {
        return name_branch;
    }

    public void setNameBranch(String name_branch) {
        this.name_branch = name_branch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
