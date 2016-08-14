package xyz.stepsecret.arrayproject3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Locale;

import xyz.stepsecret.arrayproject3.Badge.Utils;
import xyz.stepsecret.arrayproject3.Form.LoginActivity;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

/**
 * Created by stepsecret on 12/8/2559.
 */
public class ControlActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView img_message, img_notification, img_language;
    private RadioGroup rg_message, rg_notification, rg_language;
    private AppCompatRadioButton radio_s1, radio_s2, radio_s3, radio_v1, radio_v2, radio_v3;
    private AppCompatButton btn_logout;

    private TinyDB Store_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Setting");

        Store_data = new TinyDB(getApplicationContext());

        img_message = (ImageView) findViewById(R.id.img_message);
        img_notification = (ImageView) findViewById(R.id.img_notification);
        img_language = (ImageView) findViewById(R.id.img_language);

        btn_logout = (AppCompatButton) findViewById(R.id.btn_logout);

        rg_message = (RadioGroup) findViewById(R.id.rg_message);
        rg_notification = (RadioGroup) findViewById(R.id.rg_notification);
        rg_language = (RadioGroup) findViewById(R.id.rg_language);

        radio_s1 = (AppCompatRadioButton) findViewById(R.id.radio_s1);
        radio_v1 = (AppCompatRadioButton) findViewById(R.id.radio_v1);
        radio_s2 = (AppCompatRadioButton) findViewById(R.id.radio_s2);
        radio_v2 = (AppCompatRadioButton) findViewById(R.id.radio_v2);
        radio_s3 = (AppCompatRadioButton) findViewById(R.id.radio_s3);
        radio_v3 = (AppCompatRadioButton) findViewById(R.id.radio_v3);



        Glide.with(this)
                .load(R.drawable.mail_bk)
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_message);

        Glide.with(this)
                .load(R.drawable.exclamation)
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_notification);

        Glide.with(this)
                .load(R.drawable.translation)
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_language);

        rg_message.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_s1 :
                        //Log.e(" Control ","radio_s1");
                        Store_data.putString("message","sound");
                        break;
                    case R.id.radio_v1 :
                        //Log.e(" Control ","radio_v1");
                        Store_data.putString("message","vibrate");
                        break;
                    default:
                        break;
                }
            }
        });

        rg_notification.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_s2 :
                        //Log.e(" Control ","radio_s2");
                        Store_data.putString("notification","sound");
                        break;
                    case R.id.radio_v2 :
                        //Log.e(" Control ","radio_v2");
                        Store_data.putString("notification","vibrate");
                        break;
                    default:
                        break;
                }
            }
        });

        rg_language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_s3 :
                        //Log.e(" Control ","radio_s3");
                        setLanguage("th");
                        //Intent intent = new Intent(getApplicationContext(), ControlActivity.class);
                        //startActivity(intent);
                        //finish();
                        break;
                    case R.id.radio_v3 :
                        //Log.e(" Control ","radio_v3");
                        setLanguage("en");
                        //Intent intent2 = new Intent(getApplicationContext(), ControlActivity.class);
                        //startActivity(intent2);
                        //finish();
                        break;
                    default:
                        break;
                }
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Confirm_Logout();

            }
        });


        initial();

    }

    public void initial()
    {
        String message = Store_data.getString("message");
        String notification = Store_data.getString("notification");
        String language = Store_data.getString("language");

        //Log.e(" Control ",message+" "+notification+" "+language);

        if(message.equals("sound"))
        {
            radio_s1.setChecked(true);
        }
        else
        {
            radio_v1.setChecked(true);
        }

        if(notification.equals("sound"))
        {
            radio_s2.setChecked(true);
        }
        else
        {
            radio_v2.setChecked(true);
        }

        if(language.equals("en"))
        {
            radio_v3.setChecked(true);
        }
        else
        {
            radio_s3.setChecked(true);
        }



    }

    public void setLanguage(String language)
    {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        //config.setLocale(locale); // api 17
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        Store_data.putString("language", language);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem control = menu.findItem(R.id.action_control);
        MenuItem mail = menu.findItem(R.id.action_mail);

        // Obtener drawable del item
        LayerDrawable control_icon = (LayerDrawable) control.getIcon();
        LayerDrawable mail_icon = (LayerDrawable) mail.getIcon();

        // Actualizar el contador

        //Utils.setBadgeCount(this, control_icon, 3);
        //Utils.setBadgeCount(this, mail_icon, 3);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_control) {
            Log.e(" menu "," control ");

            LayerDrawable control_icon = (LayerDrawable) item.getIcon();

            Utils.setBadgeCount(this, control_icon, 0);

            Intent intent = new Intent(this, ControlActivity.class);
            startActivity(intent);
            finish();


            return true;
        }
        else if (id == R.id.action_mail) {
            Log.e(" menu "," mail ");

            LayerDrawable mail_icon = (LayerDrawable) item.getIcon();
            Utils.setBadgeCount(this, mail_icon, 0);

            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);
            finish();

            return true;
        }
        else
        {
            finish();

            return true;
        }

        //return super.onOptionsItemSelected(item);
    }

    public void Confirm_Logout()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.logout));
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setMessage(getResources().getString(R.string.ask_logout));
        dialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Logout();
            }
        });

        dialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    public void Logout()
    {

                Store_data.clear();

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();



    }
}
