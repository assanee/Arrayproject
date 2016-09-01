package xyz.stepsecret.arrayproject3.TabFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.Branch_API;
import xyz.stepsecret.arrayproject3.API.PromotionById_API;
import xyz.stepsecret.arrayproject3.BookBranch;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Model.Branch_Model;
import xyz.stepsecret.arrayproject3.Model.PromotionById_Model;
import xyz.stepsecret.arrayproject3.PromotionDetail;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.BranchPromotionAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.BranchPromotionModel;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BranchPromotionFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{


    private SliderLayout mDemoSlider;

    private int id_promotion = 0;

    private RestAdapter restAdapter;

    private TinyDB Store_data;

    private  Boolean check_have = true;

    private ArrayList<String> id_promotion_List = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.branchpromotion_content, container, false);

        mDemoSlider = (SliderLayout) v.findViewById(R.id.slider);

        //mDemoSlider.setVisibility(View.INVISIBLE);
        //mDemoSlider.setVisibility(View.GONE);


        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();

        Store_data = new TinyDB(getContext());

        check_have = true;

        load();


        return v;
    }

    public void load()
    {
        //Log.e("Load", " : " + Store_data.getString("id_branch"));

        final PromotionById_API promotionById_api = restAdapter.create(PromotionById_API.class);

        promotionById_api.Get_PromotionById_API(Store_data.getString("api_key"),Store_data.getString("id_branch"), new Callback<PromotionById_Model>() {
            @Override
            public void success(PromotionById_Model result, Response response) {

                HashMap<String,String> url_maps = new HashMap<String, String>();


                if(!result.getError() && result.getData().length > 0)
                {
                    check_have = true;

                   for (int i = 0 ; i < result.getData().length ; i++)
                   {
                       url_maps.put(result.getData()[i][2], ConfigData.Promotion+result.getData()[i][3]);


                       id_promotion_List.add(result.getData()[i][0]);
                   }

                   for(String name : url_maps.keySet())
                   {

                        TextSliderView textSliderView = new TextSliderView(getContext());
                        // initialize a SliderLayout
                        textSliderView
                                .description(name)
                                .image(url_maps.get(name))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(BranchPromotionFragment.this);

                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra",name);

                        mDemoSlider.addSlider(textSliderView);
                    }

                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.stopAutoCycle();
                    mDemoSlider.addOnPageChangeListener(BranchPromotionFragment.this);

                }
                else
                {

                    check_have = false;

                    url_maps.put("No Promotion", ConfigData.Promotion+"nodownload.png");

                    for(String name : url_maps.keySet())
                    {

                        Log.e("Load", " i : " + name);

                        TextSliderView textSliderView = new TextSliderView(getContext());
                        // initialize a SliderLayout
                        textSliderView
                                .description(name)
                                .image(url_maps.get(name))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(BranchPromotionFragment.this);

                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra",name);

                        mDemoSlider.addSlider(textSliderView);
                    }


                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.stopAutoCycle();
                    mDemoSlider.addOnPageChangeListener(BranchPromotionFragment.this);

                }

            }

            @Override
            public void failure(RetrofitError error) {

                show_failure(error.getMessage());
                Log.e(" TAG 2","failure");

            }
        });


    }


    public void show_failure(String message)
    {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {


        if(check_have == true)
        {
            Log.e(" Branch "," id_promotion : "+id_promotion_List.get(id_promotion));
            Intent intent = new Intent(getContext(), PromotionDetail.class);
            intent.putExtra("id_promotion", id_promotion_List.get(id_promotion));
            startActivity(intent);
        }


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

        Log.e("Slider Demo", "Page Changed: " + position);

        id_promotion = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {}


}
