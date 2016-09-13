package xyz.stepsecret.arrayproject3.TabFragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.CancelQueue_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Model.CancelQueue_Model;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.QueueFragment;
import xyz.stepsecret.arrayproject3.TabFragments.models.QueueModel;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.MyViewHolder> {

    private List<QueueModel> moviesList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, time, queue, number_book, name_brand, name_branch, current_queue, current_queue_number, wait_time, tv_alert1 ,tv_wait_queue;
        public ImageView img_person, img_brand, img_table, img_current_queue,  img_delete;
        public String temp_alert = "";
        public String id_queue;
        public RestAdapter restAdapter;
        public TinyDB Store_data;

        public MyViewHolder(View view) {
            super(view);

            name_brand = (TextView) view.findViewById(R.id.tv_name_brand);
            name_branch = (TextView) view.findViewById(R.id.tv_name_brand_branch);
            current_queue = (TextView) view.findViewById(R.id.tv_current_queue);
            current_queue_number = (TextView) view.findViewById(R.id.tv_current_queue_number);
            wait_time = (TextView) view.findViewById(R.id.tv_wait_time);
            tv_alert1 = (TextView) view.findViewById(R.id.tv_alert1);
            tv_wait_queue = (TextView) view.findViewById(R.id.tv_wait_queue);

            name = (TextView) view.findViewById(R.id.tv_name);
            date = (TextView) view.findViewById(R.id.tv_datetimes);
            time = (TextView) view.findViewById(R.id.tv_times);
            queue = (TextView) view.findViewById(R.id.tv_queue);
            number_book = (TextView) view.findViewById(R.id.tv_number_book);

            img_person = (ImageView) view.findViewById(R.id.img_person);
            img_brand = (ImageView) view.findViewById(R.id.img_brand);
            img_table = (ImageView) view.findViewById(R.id.img_table);
            img_current_queue = (ImageView) view.findViewById(R.id.img_current_queue);
            img_delete = (ImageView) view.findViewById(R.id.img_delete);

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ConfigData.API).build();

            Store_data = new TinyDB(mContext);




        }
    }
    public void show_failure(String message)
    {
        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }


    public QueueAdapter(Context context, List<QueueModel> moviesList) {

        this.moviesList = moviesList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.queue_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final QueueModel movie = moviesList.get(position);

        holder.id_queue = movie.getId_queue();
        holder.name_brand.setText(movie.getNamebrand());
        holder.name_branch.setText(movie.getNamebranch());
        holder.current_queue.setText(movie.getCurrent_queue());
        holder.current_queue_number.setText(movie.getCurrent_queue_number());
        holder.wait_time.setText(movie.getWait_time());

        holder.temp_alert = "";
        for(int i = 0 ; i < movie.getAlert().length ; i++ )
        {
            holder.temp_alert = holder.temp_alert + "- "+movie.getAlert()[i] +"\n";
           // Log.e(" Queue ",i+" : "+movie.getAlert()[i]);
        }
        holder.tv_alert1.setText(holder.temp_alert);
        holder.tv_wait_queue.setText(movie.getWait_queue());

        holder.date.setText(movie.getDate());
        holder.time.setText(movie.getTime());
        holder.queue.setText(movie.getQueue());
        holder.number_book.setText(movie.getNumberBook());
        holder.name.setText(movie.getFirstname()+" "+movie.getLastname());


        Glide.with(mContext)
                .load(movie.getLogoBrand())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.img_brand);

        Glide.with(mContext)
                .load(R.drawable.standingupman)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.img_person);

        Glide.with(mContext)
                .load(R.drawable.standingupman)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.img_current_queue);


        Glide.with(mContext)
                .load(R.drawable.deletebutton)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.img_delete);

        Glide.with(mContext)
                .load(R.drawable.table)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.img_table);

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.e(" Queue "," delete 222");
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(mContext.getResources().getString(R.string.areyousure))
                        .setCancelText(mContext.getResources().getString(R.string.cancel))
                        .setConfirmText(mContext.getResources().getString(R.string.sure))
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.setTitleText(mContext.getResources().getString(R.string.cancelled))
                                        .setConfirmText(mContext.getResources().getString(R.string.close))
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);


                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(final SweetAlertDialog sDialog) {

                                final CancelQueue_API cancelQueue_api = holder.restAdapter.create(CancelQueue_API.class);

                                cancelQueue_api.DeleteQueue_API(holder.Store_data.getString("api_key"),holder.id_queue, new Callback<CancelQueue_Model>() {
                                    @Override
                                    public void success(CancelQueue_Model result, Response response) {

                                        if(!result.getError()) {

                                            sDialog.setTitleText(mContext.getResources().getString(R.string.deleted))
                                                    .setConfirmText(mContext.getResources().getString(R.string.close))
                                                    .showCancelButton(false)
                                                    .setCancelClickListener(null)
                                                    .setConfirmClickListener(null)
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);


                                            QueueFragment.clearData();
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
        });


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
