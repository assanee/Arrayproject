package xyz.stepsecret.arrayproject3.TabFragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import xyz.stepsecret.arrayproject3.R;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.MyViewHolder> {

    private List<QueueModel> moviesList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, time, queue, number, name_brand, current_queue, current_queue_number, wait_time;
        public ImageView img_person, img_brand, img_table, img_current_queue, img_wait_queue, img_delete;

        public MyViewHolder(View view) {
            super(view);

            name_brand = (TextView) view.findViewById(R.id.tv_name_brand);
            current_queue = (TextView) view.findViewById(R.id.tv_current_queue);
            current_queue_number = (TextView) view.findViewById(R.id.tv_current_queue_number);
            wait_time = (TextView) view.findViewById(R.id.tv_wait_time);

            name = (TextView) view.findViewById(R.id.tv_name);
            date = (TextView) view.findViewById(R.id.tv_datetimes);
            time = (TextView) view.findViewById(R.id.tv_times);
            queue = (TextView) view.findViewById(R.id.tv_queue);
            number = (TextView) view.findViewById(R.id.tv_number_book);

            img_person = (ImageView) view.findViewById(R.id.img_person);
            img_brand = (ImageView) view.findViewById(R.id.img_brand);
            img_table = (ImageView) view.findViewById(R.id.img_table);
            img_current_queue = (ImageView) view.findViewById(R.id.img_current_queue);
            img_wait_queue = (ImageView) view.findViewById(R.id.img_wait_queue);
            img_delete = (ImageView) view.findViewById(R.id.img_delete);
        }
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        QueueModel movie = moviesList.get(position);

        holder.name_brand.setText(movie.getNamebrand());
        holder.current_queue.setText(movie.getCurrent_queue());
        holder.current_queue_number.setText(movie.getCurrent_queue_number());
        holder.wait_time.setText(movie.getWait_time());

        holder.date.setText(movie.getDate());
        holder.time.setText(movie.getTime());
        holder.queue.setText(movie.getQueue());
        holder.number.setText(movie.getNumber());
        holder.name.setText(movie.getFirstname()+" "+movie.getLastname());



        Glide.with(mContext)
                .load(R.drawable.xeuslab)
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
                .load(R.drawable.queue0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.img_wait_queue);


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



  /*Glide.with(mContext)
                .load(movie.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.nodownload)
                .into(holder.person);
*/
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
