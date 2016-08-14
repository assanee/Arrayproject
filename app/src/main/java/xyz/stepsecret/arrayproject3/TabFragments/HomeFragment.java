package xyz.stepsecret.arrayproject3.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.HomeRecyclerViewDataAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.HomeSectionDataModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.HomeSingleItemModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<HomeSectionDataModel> allSampleData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        allSampleData = new ArrayList<HomeSectionDataModel>();


        View v = inflater.inflate(R.layout.home_fragment, container, false);


        RecyclerView my_recycler_view = (RecyclerView) v.findViewById(R.id.home_my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        HomeRecyclerViewDataAdapter adapter = new HomeRecyclerViewDataAdapter(getContext(), allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        my_recycler_view.setAdapter(adapter);

        FavoriteData();
        HistoryData();
        PromotionData();

        return v;
    }

    public void FavoriteData()
    {
        HomeSectionDataModel dm = new HomeSectionDataModel();

        dm.setHeaderTitle(getResources().getString(R.string.favorite));

        ArrayList<HomeSingleItemModel> singleItem = new ArrayList<HomeSingleItemModel>();
        for (int j = 0; j <= 10; j++) {
            singleItem.add(new HomeSingleItemModel(""+j,"XeusLab", "Branch", "http://www.exotictheme.com/wp-content/uploads/2015/11/food-logo.png"));
        }

        dm.setAllItemsInSection(singleItem);


        allSampleData.add(dm);


    }

    public void HistoryData()
    {
        HomeSectionDataModel dm = new HomeSectionDataModel();

        dm.setHeaderTitle(getResources().getString(R.string.history));

        ArrayList<HomeSingleItemModel> singleItem = new ArrayList<HomeSingleItemModel>();
        for (int j = 0; j <= 10; j++) {
            singleItem.add(new HomeSingleItemModel(""+j,"XeusLab", "Branch", "http://www.exotictheme.com/wp-content/uploads/2015/11/food-logo.png"));
        }

        dm.setAllItemsInSection(singleItem);


        allSampleData.add(dm);


    }

    public void PromotionData()
    {
        HomeSectionDataModel dm = new HomeSectionDataModel();

        dm.setHeaderTitle(getResources().getString(R.string.promotion));

        ArrayList<HomeSingleItemModel> singleItem = new ArrayList<HomeSingleItemModel>();
        for (int j = 0; j <= 10; j++) {
            singleItem.add(new HomeSingleItemModel(""+j,"XeusLab", "Branch", "http://www.exotictheme.com/wp-content/uploads/2015/11/food-logo.png"));
        }

        dm.setAllItemsInSection(singleItem);


        allSampleData.add(dm);


    }




}
