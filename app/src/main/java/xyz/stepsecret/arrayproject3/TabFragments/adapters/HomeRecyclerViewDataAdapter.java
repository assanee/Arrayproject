package xyz.stepsecret.arrayproject3.TabFragments.adapters;

/**
 * Created by stepsecret on 24-12-2014.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.models.HomeSectionDataModel;

public class HomeRecyclerViewDataAdapter extends RecyclerView.Adapter<HomeRecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<HomeSectionDataModel> dataList;
    private Context mContext;

    public HomeRecyclerViewDataAdapter(Context context, ArrayList<HomeSectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.tv_name_section.setText(sectionName);

        HomeSectionListDataAdapter itemListDataAdapter = new HomeSectionListDataAdapter(mContext, singleSectionItems);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);


        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);


    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tv_name_section;

        protected RecyclerView recycler_view_list;




        public ItemRowHolder(View view) {
            super(view);

            this.tv_name_section = (TextView) view.findViewById(R.id.tv_name_section);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);



        }

    }

}