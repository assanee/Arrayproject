package xyz.stepsecret.arrayproject3.TabFragments;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.Branch_API;
import xyz.stepsecret.arrayproject3.API.Reserve_API;
import xyz.stepsecret.arrayproject3.API.Table_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Model.Branch_Model;
import xyz.stepsecret.arrayproject3.Model.Reserve_Model;
import xyz.stepsecret.arrayproject3.Model.Table_Model;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.TableAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.TableModel;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;


public class BookFragment extends Fragment {

    private static List<TableModel> TableList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TableAdapter mAdapter;

    private  RestAdapter restAdapter;
    private  TinyDB Store_data;

    private ImageView img_brand, img_right, img_left;

    private Button btn_book;

    private EditText edt_number;

    private TextView tv_name_brand, tv_name_branch;

    private int number = 0;

    public static String Id_table = "";

    public static String type_table = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.bookbranch_book, container, false);

        number = 0;

        Id_table = "";

        type_table = "";

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();

        Store_data = new TinyDB(getContext());

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        img_brand = (ImageView) v.findViewById(R.id.img_brand);
        img_right = (ImageView) v.findViewById(R.id.img_right);
        img_left = (ImageView) v.findViewById(R.id.img_left);


        btn_book = (Button) v.findViewById(R.id.btn_book);

        edt_number = (EditText) v.findViewById(R.id.edt_number);

        tv_name_brand = (TextView) v.findViewById(R.id.tv_name_brand);
        tv_name_branch = (TextView) v.findViewById(R.id.tv_name_branch);

        edt_number = (EditText) v.findViewById(R.id.edt_number);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();
        Store_data = new TinyDB(getContext());


        mAdapter = new TableAdapter(getContext(),TableList);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                final TableModel Tables = TableList.get(position);

                //Id_table = Tables.getIdtable();
                //type_table = Tables.getTable();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        tv_name_brand.setText(Store_data.getString("shopname"));
        tv_name_branch.setText(Store_data.getString("branchname"));

        Glide.with(getContext())
                .load(ConfigData.Logo+Store_data.getString("logo"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(img_brand);

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

        clearData();
        getBranch();

        return v;
    }

    public void clearData() {
        TableList.clear(); //clear list
        mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
        prepareMovieData();
    }

    private void prepareMovieData() {

        final Table_API table_api = restAdapter.create(Table_API.class);

        table_api.Get_Table_API(Store_data.getString("api_key"), Store_data.getString("id_branch"), new Callback<Table_Model>() {
            @Override
            public void success(Table_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0) {

                    for(int i = 0 ; i < result.getData().length ; i++)
                    {
                        String id_table = result.getData()[i][0];
                        String id_branch = result.getData()[i][1];
                        String table = result.getData()[i][2];
                        String wait_time = result.getData()[i][3];
                        String wait_queue = result.getData()[i][4];

                        TableModel queues = new TableModel( id_table, wait_queue, table, wait_time);
                        TableList.add(queues);


                    }

                    mAdapter.notifyDataSetChanged();

                }
                else
                {
                    //show_failure(result.getMessage());
                    Log.e(" BookFragment ","error");
                }



            }

            @Override
            public void failure(RetrofitError error) {

                //show_failure(error.getMessage());
                Log.e(" TAG ","failure");

            }
        });




    }



    public void check_input()
    {
        if(number == 0)
        {
            show_failure(" Number of Seat > 0");
        }
        else if(type_table == null || type_table.isEmpty())
        {
            show_failure(" Please select table");
        }
        else
        {
            Log.e(" Bookfragment "," > "+type_table);
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


    public void show_success(String message)
    {
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(message)
                .show();
    }

    public void show_failure(String message)
    {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private BookFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final BookFragment.ClickListener clickListener) {
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

    public void getBranch()
    {
        Log.e(" TAG ","success : start" );

        final Branch_API branch_api = restAdapter.create(Branch_API.class);

        branch_api.Get_Branch_API(Store_data.getString("api_key"),Store_data.getString("id_branch"), new Callback<Branch_Model>() {
            @Override
            public void success(Branch_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0)
                {

                    //Log.e(" Book ",""+ConfigData.Logo+result.getData()[0][8]);

                    Glide.with(getContext())
                            .load(ConfigData.Logo+result.getData()[0][8])
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .error(R.drawable.nodownload)
                            .into(img_brand);

                    tv_name_brand.setText(result.getData()[0][2]);
                    tv_name_branch.setText(result.getData()[0][3]);


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


}
