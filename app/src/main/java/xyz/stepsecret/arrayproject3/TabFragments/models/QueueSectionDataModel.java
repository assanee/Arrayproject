package xyz.stepsecret.arrayproject3.TabFragments.models;

import java.util.ArrayList;

/**
 * Created by stepsecret on 30-11-2015.
 */
public class QueueSectionDataModel {



    private String headerTitle;
    private ArrayList<QueueSingleItemModel> allItemsInSection;


    public QueueSectionDataModel() {

    }
    public QueueSectionDataModel(String headerTitle, ArrayList<QueueSingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<QueueSingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<QueueSingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
