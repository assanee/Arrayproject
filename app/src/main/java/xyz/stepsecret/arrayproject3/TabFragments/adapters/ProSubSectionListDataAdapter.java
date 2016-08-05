package xyz.stepsecret.arrayproject3.TabFragments.adapters;

/**
 * Created by stepsecret on 1/8/2559.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.models.ProSingleItemModel;

public class ProSubSectionListDataAdapter extends RecyclerView.Adapter<ProSubSectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<ProSingleItemModel> itemsList;
    private Context mContext;

    public ProSubSectionListDataAdapter(Context context, ArrayList<ProSingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pro_list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        ProSingleItemModel singleItem = itemsList.get(i);

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



        protected ImageView itemImage;




        public SingleItemRowHolder(View view) {
            super(view);


            this.itemImage = (ImageView) view.findViewById(R.id.logo_branch);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), " > "+getAdapterPosition()+" : 333", Toast.LENGTH_SHORT).show();

                }
            });



        }

    }

}