package xyz.stepsecret.arrayproject3.TabFragments.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.models.MessageModel;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<MessageModel> moviesList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_message;
        public TextView tv_message;
        public AppCompatCheckBox checkBox;
        public String id_message;
        public Boolean status;

        public MyViewHolder(View view) {
            super(view);


            tv_message = (TextView) view.findViewById(R.id.tv_message);
            img_message = (ImageView) view.findViewById(R.id.img_message);
            checkBox = (AppCompatCheckBox) view.findViewById(R.id.checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( isChecked )
                    {
                        Log.e(" Message "," Check "+id_message+" "+status);
                    }
                    else
                    {
                        Log.e(" Message "," UnCheck "+id_message+" "+status);
                    }

                }
            });

        }
    }


    public MessageAdapter(Context context, List<MessageModel> moviesList) {

        this.moviesList = moviesList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_message_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MessageModel movie = moviesList.get(position);

        holder.id_message = movie.getId();
        holder.status = movie.getStatus();
        holder.tv_message.setText(movie.getDate()+" "+movie.getTime()+" "+movie.getMessage());

        if (holder.status)
        {
            Glide.with(mContext)
                    .load(R.drawable.read)
                    .placeholder(R.drawable.nodownload)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_message);
        }
        else
        {
            Glide.with(mContext)
                    .load(R.drawable.unread)
                    .placeholder(R.drawable.nodownload)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_message);
        }





    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
