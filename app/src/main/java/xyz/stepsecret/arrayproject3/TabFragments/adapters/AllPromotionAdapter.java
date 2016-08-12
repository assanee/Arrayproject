package xyz.stepsecret.arrayproject3.TabFragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.models.AllPromotionModel;

public class AllPromotionAdapter extends RecyclerView.Adapter<AllPromotionAdapter.MyViewHolder> {

    private List<AllPromotionModel> moviesList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_promotion;
        public ImageView img_brand;
        public TextView tv_All;

        public MyViewHolder(View view) {
            super(view);


            tv_All = (TextView) view.findViewById(R.id.tv_branch);
            img_promotion = (ImageView) view.findViewById(R.id.img_promotion);
            img_brand = (ImageView) view.findViewById(R.id.img_brand);

        }
    }


    public AllPromotionAdapter(Context context, List<AllPromotionModel> moviesList) {

        this.moviesList = moviesList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allpromotion_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AllPromotionModel movie = moviesList.get(position);

       holder.tv_All.setText(movie.getBranch());

        Glide.with(mContext)
                .load(movie.getLogoPromotion())
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_promotion);

         Glide.with(mContext)
                .load(movie.getLogoBrand())
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_brand);


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
