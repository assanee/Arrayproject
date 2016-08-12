package xyz.stepsecret.arrayproject3.TabFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import xyz.stepsecret.arrayproject3.PromotionDetail;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.AllPromotionAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.AllPromotionModel;

import java.util.ArrayList;
import java.util.List;


public class AllPromotionFragment extends Fragment {

    private List<AllPromotionModel> AllPromotionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AllPromotionAdapter mAdapter;
    //private ImageView img_brand;
    //private TextView tv_brand;
    //SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View v = inflater.inflate(R.layout.allpromotion_content, container, false);


        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        //img_brand = (ImageView) v.findViewById(R.id.img_brand);
        //tv_brand = (TextView) v.findViewById(R.id.tv_name_brand);

        mAdapter = new AllPromotionAdapter(getContext(),AllPromotionList);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AllPromotionModel AllPromotions = AllPromotionList.get(position);
                Toast.makeText(getContext(),  AllPromotions.getId() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), PromotionDetail.class);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

       /* Glide.with(this)
                .load("https://stepsecret.xyz/logo xues lab.png")
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(img_brand);

        tv_brand.setText("XEUS LAB");
*/

        clearData();
        prepareMovieData();

        return v;
    }
    public void clearData() {
        AllPromotionList.clear(); //clear list
        mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }


    private void prepareMovieData() {

       // mAdapter.re

        String id = "1";
        String Logo = "https://stepsecret.xyz/logo xues lab.png";
        String branch = "Branch";
        String url = "https://www.techwyse.com/blog/wp-content/uploads/2015/10/What-are-the-Different-Types-of-Instagram-Ads.png";

        AllPromotionModel AllPromotions;
        for(int i = 0 ; i < 5 ; i++)
        {
            AllPromotions = new AllPromotionModel(i+"", Logo, branch, url);
            AllPromotionList.add(AllPromotions);

        }




        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}