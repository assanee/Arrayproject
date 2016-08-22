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
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.DeleteQueue_API;
import xyz.stepsecret.arrayproject3.API.Queue_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Model.DeleteQueue_Model;
import xyz.stepsecret.arrayproject3.Model.Queue_Model;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.QueueAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.QueueModel;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

import java.util.ArrayList;
import java.util.List;


public class QueueFragment extends Fragment {

    private static List<QueueModel> queueList = new ArrayList<>();
    private RecyclerView recyclerView;
    private static QueueAdapter mAdapter;
    private static RestAdapter restAdapter;
    private static TinyDB Store_data;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.queue_content, container, false);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();

        Store_data = new TinyDB(getContext());

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        mAdapter = new QueueAdapter(getContext(),queueList);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                final QueueModel queues = queueList.get(position);
                //Toast.makeText(getContext(), queues.getId_queue() + " is selected!", Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        clearData();


        return v;
    }
    public static void clearData() {
        queueList.clear(); //clear list
        mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
         prepareMovieData();
    }


    private static void prepareMovieData() {

        ////////////////////////////////////////////

        Log.e(" TAG ","success : start" );

        final Queue_API queue_api = restAdapter.create(Queue_API.class);

        queue_api.Get_Queue_API(Store_data.getString("api_key"), new Callback<Queue_Model>() {
            @Override
            public void success(Queue_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0) {

                    String[] alert = new String[]{
                            "05/12/2016 12:32:22 A010 Your Queue is on service.",
                            "05/12/2016 12:26:26 A010 5 Queue to go." ,
                            "05/12/2016 12:01:13 Thank you for registed. You Queue is A010 - 6 waiting queue (30 min)"};


                    for(int i = 0 ; i < result.getData().length ; i++)
                    {
                        String id_queue = result.getData()[i][0];
                        String current_queue_table = "1-2";
                        String name_brand = result.getData()[i][8];
                        String name_branch = result.getData()[i][9];
                        String current_queue = "A009";
                        String current_queue_number = "3";
                        String wait_time = "30";
                        String wait_queue = "9";
                        String firstname = Store_data.getString("firstname");
                        String lastname = Store_data.getString("lastname");
                        String date = result.getData()[i][5];
                        String time = result.getData()[i][6];
                        String queue = result.getData()[i][3];
                        String number_book = result.getData()[i][2];
                        String logo_brand = ConfigData.Logo+result.getData()[i][7];

                        QueueModel queues = new QueueModel(id_queue, alert, current_queue_table, name_brand, name_branch, current_queue, current_queue_number, wait_time, wait_queue, firstname, lastname, date, time, queue, number_book, logo_brand);
                        queueList.add(queues);
                    }




                    mAdapter.notifyDataSetChanged();

                }
                else
                {
                    //show_failure(result.getMessage());
                    Log.e(" TAG ","error");
                }



            }

            @Override
            public void failure(RetrofitError error) {

                //show_failure(error.getMessage());
                Log.e(" TAG ","failure");

            }
        });
    }





    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private QueueFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final QueueFragment.ClickListener clickListener) {
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
