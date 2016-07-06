package xyz.stepsecret.arrayproject3.Form;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import xyz.stepsecret.arrayproject3.R;

import butterknife.ButterKnife;
import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.Signup_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Date.DatePickerFragment;
import xyz.stepsecret.arrayproject3.Model.Signup_Model;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private String store_gender = "";
    private RestAdapter restAdapter;

    @Bind(R.id.input_username) EditText _username;
    @Bind(R.id.input_first_name) EditText _firstname;
    @Bind(R.id.input_last_name) EditText _lastname;
    @Bind(R.id.input_birthday) EditText _birthday;
    @Bind(R.id.input_gender) Spinner _genger;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_repassword) EditText _repasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;

    @Bind(R.id.link_login) TextView _loginLink;

    @Bind(R.id.img_date) ImageView _image_date;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        _image_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        _genger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {

                store_gender = adapter.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void signup() {
        Log.d(TAG, "Signup");

        String username = _username.getText().toString();
        String firstname = _firstname.getText().toString();
        String lastname = _lastname.getText().toString();
        String birthday = _birthday.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String repassword = _repasswordText.getText().toString();

        if (!validate(username, firstname, lastname, birthday, email, password, repassword)) {
            onSignupFailed();
            return;
        }
        else
        {


            Call_Signup(username, firstname, lastname, birthday, store_gender, email, password,
                    repassword, "customer");
        }

    }

    public void Call_Signup(String username, String firstname, String lastname, String birthday,
                            String gender, String email, String password, String repassword,
                            String class_data)
    {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();

        final Signup_API signup_api = restAdapter.create(Signup_API.class);

        signup_api.SignUP(username, firstname, lastname, birthday, gender, email, password,
                repassword,class_data, new Callback<Signup_Model>() {
            @Override
            public void success(Signup_Model result, Response response) {

                if(!result.getError())
                {

                    show_success(result.getMessage());
                }
                else
                {
                    show_failure(result.getMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {

                Log.e(" TAG ","failure "+error.getMessage());
                show_failure(error.getMessage());
            }
        });
    }

    public void show_success(String message)
    {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(message)
                .setContentText("Close to login")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    public void show_failure(String message)
    {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }


    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

    }

    public boolean validate(String username, String firstname, String lastname, String birthday,
                            String email, String password, String repassword) {
        boolean valid = true;



        if (username.isEmpty() || username.length() < 3) {
            _username.setError("at least 3 characters");
            valid = false;
        } else {
            _username.setError(null);
        }

        if (firstname.isEmpty() || firstname.length() < 3) {
            _firstname.setError("at least 3 characters");
            valid = false;
        } else {
            _firstname.setError(null);
        }

        if (lastname.isEmpty() || lastname.length() < 3) {
            _lastname.setError("at least 3 characters");
            valid = false;
        } else {
            _lastname.setError(null);
        }

        String[] parts = birthday.split("-");
        if( birthday.isEmpty() || parts[0].length() != 4)
        {
            _birthday.setError("year is failure");
        }
        else if(parts[1].length() < 1 || parts[1].length() > 2)
        {
            _birthday.setError("month is failure");
        }
        else if(parts[2].length() < 1 || parts[2].length() > 2 )
        {
            _birthday.setError("day is failure");
        }
        else
        {
            _birthday.setError(null);
        }




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

        if (repassword.isEmpty() || repassword.length() < 4 || repassword.length() > 10) {
            _repasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _repasswordText.setError(null);
        }

        if(!password.equals(repassword))
        {
            _passwordText.setError("password not macth");
            _repasswordText.setError("password not macth");
        }
        return valid;
    }
}