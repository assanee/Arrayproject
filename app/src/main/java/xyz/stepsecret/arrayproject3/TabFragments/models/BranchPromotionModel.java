package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by stepsecret on 15/01/16.
 */
public class BranchPromotionModel {
    private String  id, Logo_brand, branch, logo_promotion;

    public BranchPromotionModel() {
    }

    public BranchPromotionModel(String id, String Logo_brand, String branch, String logo_promotion) {

        this.id = id;
        this.branch = branch;
        this.Logo_brand = Logo_brand;
        this.logo_promotion = logo_promotion;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogoBrand() {
        return Logo_brand;
    }

    public void setLogoBrand(String Logo_brand) {
        this.Logo_brand = Logo_brand;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLogoPromotion() {
        return logo_promotion;
    }

    public void setLogoPromotion(String logo_promotion) {
        this.logo_promotion = logo_promotion;
    }


}
