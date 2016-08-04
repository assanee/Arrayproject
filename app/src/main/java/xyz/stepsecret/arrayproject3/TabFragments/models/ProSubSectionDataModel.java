package xyz.stepsecret.arrayproject3.TabFragments.models;

import java.util.ArrayList;

/**
 * Created by stepsecret on 1/8/2559.
 */
public class ProSubSectionDataModel {



    private String headerTitle;
    private ArrayList<ProSubSingleItemModel> allItemsInSection;


    public ProSubSectionDataModel() {

    }
    public ProSubSectionDataModel(String headerTitle, ArrayList<ProSubSingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<ProSubSingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<ProSubSingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}

