package xyz.stepsecret.arrayproject3.TabFragments.adapters;

/**
 * Created by stepsecret on 24-12-2014.
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.ChangeFavorite_API;
import xyz.stepsecret.arrayproject3.BookBranch;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Model.ChangeFavorite_Model;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSingleItemModel;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

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

        holder.id_branch = singleItem.getIdbranch();

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
        protected ImageView itemImage, overflow;

        protected String id;

        protected String id_branch;

        public SingleItemRowHolder(View view) {
            super(view);

            this.tv_name_brand = (TextView) view.findViewById(R.id.tv_name_brand);
            this.tv_name_brand_branch = (TextView) view.findViewById(R.id.tv_name_brand_branch);
            this.itemImage = (ImageView) view.findViewById(R.id.logo_branch);
            this.overflow = (ImageView) view.findViewById(R.id.overflow);

            this.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showPopupMenu(overflow,id_branch);

                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //Toast.makeText(v.getContext(), tv_name_brand.getText()+" > "+getAdapterPosition()+" : "+id, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, BookBranch.class);
                    intent.putExtra("id_branch", id_branch);
                    mContext.startActivity(intent);


                    //Log.e(" Shop ","");

                }
            });



        }

    }


    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, String id_branch) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_option, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(id_branch));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private String id_branch;
        private RestAdapter restAdapter;
        private TinyDB Store_data;

        public MyMenuItemClickListener(String id_branch) {

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ConfigData.API).build();

            Store_data = new TinyDB(mContext);

            this.id_branch = id_branch;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    ChangeFavorite(id_branch);
                    return true;
                default:
            }
            return false;
        }


        public void show_success(String message)
        {
            new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(message)
                    .show();
        }

        public void show_failure(String message)
        {
            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(message)
                    .show();
        }

        public void ChangeFavorite(String id_branch)
        {

            final ChangeFavorite_API changeFavorite_api = restAdapter.create(ChangeFavorite_API.class);

            changeFavorite_api.ChangeFavorite_API(Store_data.getString("api_key"),id_branch, new Callback<ChangeFavorite_Model>() {
                @Override
                public void success(ChangeFavorite_Model result, Response response) {

                    if(!result.getError()) {

                        show_success(result.getMessage());
                    }
                    else
                    {
                        show_failure(result.getMessage());

                    }



                }

                @Override
                public void failure(RetrofitError error) {

                    show_failure(error.getMessage());
                    Log.e(" PromotionData ","failure ");

                }
            });


        }


    }


}