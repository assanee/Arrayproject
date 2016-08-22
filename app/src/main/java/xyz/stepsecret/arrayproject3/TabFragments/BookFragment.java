package xyz.stepsecret.arrayproject3.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.Branch_API;
import xyz.stepsecret.arrayproject3.API.DeleteQueue_API;
import xyz.stepsecret.arrayproject3.API.Reserve_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Model.Branch_Model;
import xyz.stepsecret.arrayproject3.Model.DeleteQueue_Model;
import xyz.stepsecret.arrayproject3.Model.Reserve_Model;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;


public class BookFragment extends Fragment {

    private LinearLayout ln1;
    private LinearLayout ln2;
    private LinearLayout ln3;

    private ImageView img_brand, img_right, img_left;

    private TextView tv_name_brand, tv_name_brand_branch, tv_wait_queue1, tv_wait_queue2, tv_wait_queue3, tv_wait_time1, tv_wait_time2, tv_wait_time3;

    private Button btn_book;

    private EditText edt_number;

    private RestAdapter restAdapter;

    private TinyDB Store_data;

    private int number = 0;

    private String type_table;

    public BookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.bookbranch_book, container, false);

        ln1 = (LinearLayout) v.findViewById(R.id.ln_1);
        ln2 = (LinearLayout) v.findViewById(R.id.ln_2);
        ln3 = (LinearLayout) v.findViewById(R.id.ln_3);

        img_brand = (ImageView) v.findViewById(R.id.img_brand);
        img_right = (ImageView) v.findViewById(R.id.img_right);
        img_left = (ImageView) v.findViewById(R.id.img_left);

        tv_name_brand = (TextView) v.findViewById(R.id.tv_name_brand);
        tv_name_brand_branch = (TextView) v.findViewById(R.id.tv_name_brand_branch);
        tv_wait_queue1 = (TextView) v.findViewById(R.id.tv_wait_queue1);
        tv_wait_queue2 = (TextView) v.findViewById(R.id.tv_wait_queue2);
        tv_wait_queue3 = (TextView) v.findViewById(R.id.tv_wait_queue3);
        tv_wait_time1 = (TextView) v.findViewById(R.id.tv_wait_time1);
        tv_wait_time2 = (TextView) v.findViewById(R.id.tv_wait_time2);
        tv_wait_time3 = (TextView) v.findViewById(R.id.tv_wait_time3);

        btn_book = (Button) v.findViewById(R.id.btn_book);

        edt_number = (EditText) v.findViewById(R.id.edt_number);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();
        Store_data = new TinyDB(getContext());



        ln1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ln1.setBackgroundResource(R.drawable.rounded_corner_red);
                ln2.setBackgroundResource(R.drawable.rounded_corner_no);
                ln3.setBackgroundResource(R.drawable.rounded_corner_no);

                type_table = "1-2";

            }
        });

        ln2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ln1.setBackgroundResource(R.drawable.rounded_corner_no);
                ln2.setBackgroundResource(R.drawable.rounded_corner_red);
                ln3.setBackgroundResource(R.drawable.rounded_corner_no);

                type_table = "3-4";

            }
        });

        ln3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ln1.setBackgroundResource(R.drawable.rounded_corner_no);
                ln2.setBackgroundResource(R.drawable.rounded_corner_no);
                ln3.setBackgroundResource(R.drawable.rounded_corner_red);

                type_table = "6+";

            }
        });

        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    number++;
                    edt_number.setText(""+number);

            }
        });

        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(number != 0)
                {
                    number--;
                    edt_number.setText(""+number);
                }

            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                check_input();

            }
        });

        getBranch();

        return v;
    }

    public void check_input()
    {
        if(number == 0)
        {
            show_failure(" Number of Seat > 0");
        }
        else if(type_table == null)
        {
            show_failure(" Please select table");
        }
        else
        {
            alert_book();
        }
    }

    public void alert_book()
    {

        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.areyousure))
                .setCancelText(getResources().getString(R.string.no))
                .setConfirmText(getResources().getString(R.string.yes))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.setTitleText(getResources().getString(R.string.cancelled))
                                .setConfirmText(getResources().getString(R.string.close))
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);


                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {

                        final Reserve_API reserve_api = restAdapter.create(Reserve_API.class);

                        reserve_api.Reserve_API(Store_data.getString("api_key"),Store_data.getString("id_branch"),type_table,edt_number.getText().toString(), new Callback<Reserve_Model>() {
                            @Override
                            public void success(Reserve_Model result, Response response) {

                                if(!result.getError()) {

                                    sDialog.setTitleText(getResources().getString(R.string.reserve))
                                            .setConfirmText(getResources().getString(R.string.close))
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);



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
                })
                .show();
    }

    public void getBranch()
    {
        Log.e(" TAG ","success : start" );

        final Branch_API branch_api = restAdapter.create(Branch_API.class);

        branch_api.Get_Branch_API(Store_data.getString("api_key"),Store_data.getString("id_branch"), new Callback<Branch_Model>() {
            @Override
            public void success(Branch_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0)
                {

                    Glide.with(getContext())
                            .load(ConfigData.Logo+result.getData()[0][8])
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .error(R.drawable.nodownload)
                            .into(img_brand);

                    tv_name_brand.setText(result.getData()[0][2]);
                    tv_name_brand_branch.setText(result.getData()[0][3]);

                    tv_wait_queue1.setText("2");
                    tv_wait_queue2.setText("3");
                    tv_wait_queue3.setText("4");

                    tv_wait_time1.setText("20");
                    tv_wait_time2.setText("25");
                    tv_wait_time3.setText("40");

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


    public void show_failure(String message)
    {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }


}
