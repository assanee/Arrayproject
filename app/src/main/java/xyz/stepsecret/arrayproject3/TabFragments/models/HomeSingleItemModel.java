package xyz.stepsecret.arrayproject3.TabFragments.models;

import xyz.stepsecret.arrayproject3.Config.ConfigData;

/**
 * Created by stepsecret on 01-12-2015.
 */
public class HomeSingleItemModel {


    private String name_brand;
    private String name_branch;
    private String url;
    private String view_type;
    private String description;
    private String id;
    private String id_branch;


    public HomeSingleItemModel() {
    }

    public HomeSingleItemModel(String id, String id_branch, String name_brand, String name_branch, String url, String view_type) {
        this.id = id;
        this.id_branch = id_branch;
        this.name_brand = name_brand;
        this.name_branch = name_branch;
        this.url = url;
        this.view_type = view_type;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdbranch() {
        return id_branch;
    }

    public void setIdbranch(String id_branch) {
        this.id_branch = id_branch;
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


    public String getViewType() {
        return view_type;
    }

    public void setViewType(String view_type) {
        this.view_type = view_type;
    }


}
