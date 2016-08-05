package xyz.stepsecret.arrayproject3.TabFragments.adapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSingleItemModel;

public class ShopSectionListDataAdapter extends RecyclerView.Adapter<ShopSectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<ShopSingleItemModel> itemsList;
    private Context mContext;

    public ShopSectionListDataAdapter(Context context, ArrayList<ShopSingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        ShopSingleItemModel singleItem = itemsList.get(i);

        holder.tv_name_brand.setText(singleItem.getName_branch());
        holder.tv_name_brand_branch.setText(singleItem.getName_brand_branch());

        holder.id = singleItem.getId();



       /* Picasso.with(mContext)
                .load(singleItem.getUrl())
                .placeholder(R.drawable.nodownload)
                .error(R.drawable.nodownload)
                .into(holder.itemImage);
*/
        Glide.with(mContext)
                .load(singleItem.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.nodownload)
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tv_name_brand;
        protected TextView tv_name_brand_branch;
        protected ImageView itemImage;

        protected String id;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tv_name_brand = (TextView) view.findViewById(R.id.tv_name_brand);
            this.tv_name_brand_branch = (TextView) view.findViewById(R.id.tv_name_brand_branch);
            this.itemImage = (ImageView) view.findViewById(R.id.logo_branch);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), tv_name_brand.getText()+" > "+getAdapterPosition()+" : "+id, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, BookBranch.class);
                    mContext.startActivity(intent);


                    Log.e(" Shop ","");

                }
            });



        }

    }

}