package xyz.stepsecret.arrayproject3.TabFragments.adapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import xyz.stepsecret.arrayproject3.BookBranch;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.models.HomeSingleItemModel;

public class HomeSectionListDataAdapter extends RecyclerView.Adapter<HomeSectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<HomeSingleItemModel> itemsList;
    private Context mContext;


    public HomeSectionListDataAdapter(Context context, ArrayList<HomeSingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        HomeSingleItemModel singleItem = itemsList.get(i);

        if(singleItem.getViewType().equals("normal"))
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_list_single_card, null);
            SingleItemRowHolder mh = new SingleItemRowHolder(v);
            return mh;
        }
        else
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_list_single_card_promotion, null);
            SingleItemRowHolder mh = new SingleItemRowHolder(v);

            return mh;
        }



    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        HomeSingleItemModel singleItem = itemsList.get(i);

        holder.tv_name_brand.setText(singleItem.getNameBrand());
        holder.tv_name_brand_branch.setText(singleItem.getNameBranch());

        holder.id = singleItem.getId();

        holder.id_branch = singleItem.getIdbranch();

        holder.view_type = singleItem.getViewType();

        Glide.with(mContext)
                .load(singleItem.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.nodownload)
                .into(holder.logo_branch);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tv_name_brand, tv_name_brand_branch;

        protected ImageView logo_branch, overflow;

        protected String id;

        protected String id_branch;

        protected String view_type = "normal";


        public SingleItemRowHolder(View view) {
            super(view);

            this.tv_name_brand = (TextView) view.findViewById(R.id.tv_name_brand);
            this.tv_name_brand_branch = (TextView) view.findViewById(R.id.tv_name_brand_branch);
            this.logo_branch = (ImageView) view.findViewById(R.id.logo_branch);
            this.overflow = (ImageView) view.findViewById(R.id.overflow);


            this.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showPopupMenu(overflow);

                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(view_type.equals("normal"))
                    {
                        Intent intent = new Intent(mContext, BookBranch.class);
                        intent.putExtra("id_branch", id_branch);
                        mContext.startActivity(intent);
                    }
                    else
                    {
                        Log.e(" Home1 "," : "+view_type);
                    }


                }
            });



        }



    }



    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_option, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite id : ", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

}