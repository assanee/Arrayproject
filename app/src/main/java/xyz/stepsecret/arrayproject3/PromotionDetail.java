package xyz.stepsecret.arrayproject3;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.zxing.WriterException;
import com.metalurgus.longtextview.LongTextView;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.PromotionById_API;
import xyz.stepsecret.arrayproject3.API.PromotionDetail_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Model.PromotionById_Model;
import xyz.stepsecret.arrayproject3.Model.PromotionDetail_Model;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

/**
 * Created by stepsecret on 10/8/2559.
 */
public class PromotionDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView img_promotion;
    private LongTextView longTextView;
    private LongTextView tv_head;

    private RestAdapter restAdapter;

    private TinyDB Store_data;

    private String id_promotion = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_detail_new);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Promotion Detail");

        id_promotion = getIntent().getExtras().getString("id_promotion");


        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();

        Store_data = new TinyDB(this);

        img_promotion = (ImageView) findViewById(R.id.img_promotion);
        longTextView = (LongTextView) findViewById(R.id.view);
        tv_head = (LongTextView) findViewById(R.id.tv_head);


        load();

    }


    public void load()
    {
        //Log.e("Load", " : " + Store_data.getString("id_branch"));

        final PromotionDetail_API promotionDetail_api = restAdapter.create(PromotionDetail_API.class);

        promotionDetail_api.Get_PromotionDetail_API(Store_data.getString("api_key"),id_promotion, new Callback<PromotionDetail_Model>() {
            @Override
            public void success(PromotionDetail_Model result, Response response) {


                if(!result.getError() && result.getData().length > 0)
                {

                    Glide.with(PromotionDetail.this)
                            .load(ConfigData.Promotion+result.getData()[0][3])
                            .placeholder(R.drawable.nodownload)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img_promotion);

                    tv_head.setText(result.getData()[0][2]);

                    longTextView.setText(result.getData()[0][5]);


                }
                else
                {


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
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        return true;


    }

}
