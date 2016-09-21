package xyz.stepsecret.arrayproject3.TabFragments.models;

import java.util.ArrayList;

/**
 * Created by stepsecret on 30-11-2015.
 */
public class ShopSectionDataModel {



    private String headerTitle;
    private ArrayList<ShopSingleItemModel> allItemsInSection;


    public ShopSectionDataModel() {

    }
    public ShopSectionDataModel(String headerTitle, ArrayList<ShopSingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<ShopSingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<ShopSingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
