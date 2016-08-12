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

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.WriterException;

import net.glxn.qrgen.android.QRCode;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * Created by stepsecret on 10/8/2559.
 */
public class PromotionDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView img_brand, img_promotion, img_qr;
    private Bitmap myBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_detail);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Promotion Detail");

        img_brand = (ImageView) findViewById(R.id.img_brand);
        img_promotion = (ImageView) findViewById(R.id.img_promotion);
        img_qr = (ImageView) findViewById(R.id.img_qr);

        Glide.with(this)
                .load("https://stepsecret.xyz/logo xues lab.png")
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_brand);

        Glide.with(this)
                .load("https://s-media-cache-ak0.pinimg.com/236x/68/78/be/6878be6f9e47fae599902cf87011d399.jpg")
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_promotion);

        myBitmap = QRCode.from("56453148641514894564894984").bitmap();

        img_qr.setImageBitmap(myBitmap);


        img_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //img_qr.setVisibility(View.GONE);
                //img_promotion.setImageBitmap(myBitmap);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        MaterialDialog dialog = new MaterialDialog.Builder(PromotionDetail.this)
                                .customView(R.layout.dialog_qrcode, true)
                                .positiveText("Exit")
                                .build();

                        dialog.show();

                        ImageView img_qr_show = (ImageView) dialog.findViewById(R.id.img_qr_show);

                        img_qr_show.setImageBitmap(myBitmap);
                    }
                }, 1000); // starting it in 1 second




            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        return true;


    }

}
