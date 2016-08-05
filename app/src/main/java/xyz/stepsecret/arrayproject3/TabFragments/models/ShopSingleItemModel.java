package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class ShopSingleItemModel {


    private String name_brand;
    private String name_brand_branch;
    private String url;
    private String id;


    public ShopSingleItemModel() {
    }

    public ShopSingleItemModel(String id, String name_brand,String name_brand_branch, String url) {
        this.id = id;
        this.name_brand = name_brand;
        this.url = url;
        this.name_brand_branch = name_brand_branch;
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

    public String getName_branch() {
        return name_brand;
    }

    public void setName_branch(String name_brand) {
        this.name_brand = name_brand;
    }

    public String getName_brand_branch() {
        return name_brand_branch;
    }

    public void setName_brand_branch(String name_brand_branch) {
        this.name_brand_branch = name_brand_branch;
    }


}
