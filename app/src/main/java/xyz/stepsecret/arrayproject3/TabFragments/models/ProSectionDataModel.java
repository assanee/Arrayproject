package xyz.stepsecret.arrayproject3.TabFragments.models;

import java.util.ArrayList;

/**
 * Created by stepsecret on 30-11-2015.
 */
public class ProSectionDataModel {



    private String headerTitle;
    private ArrayList<ProSingleItemModel> allItemsInSection;


    public ProSectionDataModel() {

    }
    public ProSectionDataModel(String headerTitle, ArrayList<ProSingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<ProSingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<ProSingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
