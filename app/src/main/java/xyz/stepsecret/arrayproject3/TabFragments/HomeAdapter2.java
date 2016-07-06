package xyz.stepsecret.arrayproject3.TabFragments;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import xyz.stepsecret.arrayproject3.R;

import java.util.List;

public class HomeAdapter2 extends RecyclerView.Adapter<HomeAdapter2.ItemViewHolder> {

    private List<String> mItems;

    public  Context context;

    public HomeAdapter2(List<String> mItems) {
        this.mItems = mItems;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ImageView mImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.title);
            mImg = (ImageView) itemView.findViewById(R.id.shop_image);

            context = itemView.getContext();
        }
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.mTextView.setText(mItems.get(i));

        Picasso.with(context)
                .load("https://inthecheesefactory.com/uploads/source/designlibrary/screenshot32.jpg")
                .placeholder(R.drawable.nodownload)
                .error(R.drawable.nodownload)
                .into(itemViewHolder.mImg);


    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item2, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
