package xyz.stepsecret.arrayproject3.TabFragments.models;

import java.util.ArrayList;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class HomeSectionDataModel {



    private String headerTitle;
    private ArrayList<HomeSingleItemModel> allItemsInSection;


    public HomeSectionDataModel() {

    }
    public HomeSectionDataModel(String headerTitle, ArrayList<HomeSingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<HomeSingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<HomeSingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
