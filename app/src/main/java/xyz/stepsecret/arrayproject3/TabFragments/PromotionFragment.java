package xyz.stepsecret.arrayproject3.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.HomeRecyclerViewDataAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.ProRecyclerViewDataAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.HomeSectionDataModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.HomeSingleItemModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.ProSectionDataModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.ProSingleItemModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.ProSubSectionDataModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.ProSubSingleItemModel;

/**
 * Created by iFocus on 27-10-2015.
 */
public class PromotionFragment extends Fragment {

    ArrayList<ProSectionDataModel> allSampleData;
    ArrayList<ProSubSectionDataModel> allSampleDataSub;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        allSampleData = new ArrayList<ProSectionDataModel>();
        allSampleDataSub = new ArrayList<ProSubSectionDataModel>();


        View v = inflater.inflate(R.layout.promotion_fragment, container, false);


        RecyclerView my_recycler_view = (RecyclerView) v.findViewById(R.id.pro_my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        ProRecyclerViewDataAdapter adapter = new ProRecyclerViewDataAdapter(getContext(), allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
       // my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        my_recycler_view.setAdapter(adapter);


        createDummyData();

        return v;
    }
    public void createDummyData() {
        //for (int i = 1; i <= 10; i++) {

            ProSectionDataModel dm = new ProSectionDataModel();

            dm.setHeaderTitle("Section " + 1);

            ArrayList<ProSingleItemModel> singleItem = new ArrayList<ProSingleItemModel>();
            for (int j = 0; j <= 10; j++) {
                singleItem.add(new ProSingleItemModel("id = "+1,"Item+ " + j, "http://www.exotictheme.com/wp-content/uploads/2015/11/food-logo.png"));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        for (int i = 1; i <= 10; i++) {

        ProSubSectionDataModel dm1 = new ProSubSectionDataModel();

        dm.setHeaderTitle("Section " + 1);

        ArrayList<ProSubSingleItemModel> singleItem1 = new ArrayList<ProSubSingleItemModel>();

            singleItem1.add(new ProSubSingleItemModel("id = "+1,"Item+ " + 1, "http://www.exotictheme.com/wp-content/uploads/2015/11/food-logo.png"));


        dm1.setAllItemsInSection(singleItem1);

        allSampleDataSub.add(dm1);


         }


       // }
    }


}
