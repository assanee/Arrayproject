package xyz.stepsecret.arrayproject3.Form;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.Login_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.MainActivity;
import xyz.stepsecret.arrayproject3.Model.Login_Model;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private RestAdapter restAdapter;

    private TinyDB Store_data;

    private String temp_language;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);



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


        Check_login();

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });


    }

    public void Check_login()
    {
        Boolean login = Store_data.getBoolean("login", false);

        if(login==true)
        {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }



        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.


        Call_login(email,password);
    }

    public void Call_login(String email,String password)
    {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();

        final Login_API login_api = restAdapter.create(Login_API.class);

        login_api.SignIN(email, password, new Callback<Login_Model>() {
            @Override
            public void success(Login_Model result, Response response) {

                if(!result.getError())
                {
                    Log.e(" TAG ","success");
                    Log.e(" TAG ",result.getUsername());
                    Log.e(" TAG ",result.getFirst_name());
                    Log.e(" TAG ",result.getLast_name());
                    Log.e(" TAG ",result.getEmail());
                    Log.e(" TAG ",result.getApiKey());
                    Log.e(" TAG ",result.getClass_data());
                    Log.e(" TAG ",result.getCreatedAt());

                    Store_data.putBoolean("login", true);
                    Store_data.putString("username", result.getUsername());
                    Store_data.putString("firstname", result.getFirst_name());
                    Store_data.putString("lastname", result.getLast_name());
                    Store_data.putString("email", result.getEmail());
                    Store_data.putString("api_key", result.getApiKey());
                    Store_data.putString("class", result.getClass_data());

                    Check_login();
                }
                else
                {
                    show_failure(result.getMessage());
                    Log.e(" TAG ","error");
                }



            }

            @Override
            public void failure(RetrofitError error) {

                show_failure(error.getMessage());
                Log.e(" TAG ","failure");

            }
        });
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

    public void show_failure(String message)
    {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
