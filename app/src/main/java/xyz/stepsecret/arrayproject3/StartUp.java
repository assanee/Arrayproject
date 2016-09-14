package xyz.stepsecret.arrayproject3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import xyz.stepsecret.arrayproject3.Form.LoginActivity;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

/**
 * Created by stepsecret on 14/9/2559.
 */
public class StartUp extends AppCompatActivity {

    private SweetAlertDialog pDialog;

    private TinyDB Store_data;

    private String temp_language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pDialog();

        Initial_();

        showGPSDisabledAlertToUser();


    }
    private void showGPSDisabledAlertToUser(){

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {

            new CountDownTimer(5000, 1000){
                public void onTick(long millisUntilDone){

                    Log.e(" StartUp "," "+millisUntilDone);
                }

                public void onFinish() {

                    Log.e(" StartUp "," onFinish");
                    pDialog.cancel();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();

                }
            }.start();
        }
        else
        {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Turn On GPS");
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.setCancelable(true);
            dialog.setMessage("Please!!! Turn On GPS");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 1);
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            dialog.show();


        }


    }


    private void pDialog()
    {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
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

        //Store_data.putString("language", language);

    }

    private void Initial_()
    {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(LoginActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //Toast.makeText(LoginActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };


        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("we need permission for write external storage and find your location")
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("Go to setting")
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();


        Store_data = new TinyDB(getApplicationContext());

        temp_language = Store_data.getString("language");
        if(temp_language != null && !temp_language.isEmpty())
        {
            setLanguage(temp_language);
        }
        else
        {


            Store_data.putString("message", "sound");
            Store_data.putString("notification", "sound");
            Store_data.putString("language", "en");
            Store_data.putInt("number_message", 0);

            setLanguage("en");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        showGPSDisabledAlertToUser();

    }


}
