package xyz.stepsecret.arrayproject3.TabFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import xyz.stepsecret.arrayproject3.MapActivity;
import xyz.stepsecret.arrayproject3.MessageActivity;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.ShopRecyclerViewDataAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSectionDataModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSingleItemModel;

/**
 * Created by iFocus on 27-10-2015.
 */
public class ShopFragment extends Fragment {


    private ArrayList<ShopSectionDataModel> allSampleData;

    private SearchView search_shop;
    private SearchView search_branch;
    private RecyclerView my_recycler_view;
    private ShopRecyclerViewDataAdapter adapter;
    private ImageView img_map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.shop_fragment, container, false);


        allSampleData = new ArrayList<ShopSectionDataModel>();
        search_shop = (SearchView) v.findViewById(R.id.search_shop);
        search_branch = (SearchView) v.findViewById(R.id.search_branch);

        my_recycler_view = (RecyclerView) v.findViewById(R.id.shop_my_recycler_view);

        img_map = (ImageView) v.findViewById(R.id.img_map);

        my_recycler_view.setHasFixedSize(true);

        adapter = new ShopRecyclerViewDataAdapter(getContext(), allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        my_recycler_view.setAdapter(adapter);


        search_shop.setQueryHint(getResources().getString(R.string.shop_search));
        search_branch.setQueryHint(getResources().getString(R.string.branch_search));
        search_branch.setVisibility(View.GONE);

        search_shop.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_LONG).show();
                search_branch.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });


        search_branch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });


        Glide.with(this)
                .load(R.drawable.map)
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_map);



        img_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
                //finish();

            }
        });

        createFavorite();
        createNear();

        return v;
    }

    public void createFavorite() {


            ShopSectionDataModel dm = new ShopSectionDataModel();

            dm.setHeaderTitle(getResources().getString(R.string.favorite));

            ArrayList<ShopSingleItemModel> singleItem = new ArrayList<ShopSingleItemModel>();
            for (int j = 0; j <= 10; j++) {

                singleItem.add(new ShopSingleItemModel("id = ","XeusXeus1" , "Branch" , "http://www.exotictheme.com/wp-content/uploads/2015/11/food-logo.png"));
            }

            dm.setAllItemsInSection(singleItem);


            allSampleData.add(dm);



    }
    public void createNear() {


            ShopSectionDataModel dm = new ShopSectionDataModel();

            dm.setHeaderTitle(getResources().getString(R.string.near));

            ArrayList<ShopSingleItemModel> singleItem = new ArrayList<ShopSingleItemModel>();
            for (int j = 0; j <= 10; j++) {

                singleItem.add(new ShopSingleItemModel("id = ","XeusXeus1XeusXeus1XeusXeus1" , "Branch" , "http://www.exotictheme.com/wp-content/uploads/2015/11/food-logo.png"));
            }

            dm.setAllItemsInSection(singleItem);


            allSampleData.add(dm);



    }


}
