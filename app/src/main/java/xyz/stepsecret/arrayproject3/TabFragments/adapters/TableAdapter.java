package xyz.stepsecret.arrayproject3.TabFragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.BookFragment;
import xyz.stepsecret.arrayproject3.TabFragments.models.TableModel;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder> {

    private List<TableModel> tablelist;
    private Context mContext;
    private LinearLayout ln_temp;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  tv_table, tv_wait_queue, tv_wait_time;
        public String id_table;
        public LinearLayout ln1;
        public Boolean Check_table = true;

        public MyViewHolder(View view) {
            super(view);

            tv_table = (TextView) view.findViewById(R.id.tv_table);
            tv_wait_queue = (TextView) view.findViewById(R.id.tv_wait_queue);
            tv_wait_time = (TextView) view.findViewById(R.id.tv_wait_time);

            ln1 = (LinearLayout) view.findViewById(R.id.ln_1);


        }
    }
    public void show_failure(String message)
    {
        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }


    public TableAdapter(Context context, List<TableModel> tablelist) {

        this.tablelist = tablelist;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final TableModel tableModel = tablelist.get(position);

        holder.id_table = tableModel.getIdtable();

        holder.tv_table.setText(tableModel.getTable());
        holder.tv_wait_queue.setText(mContext.getResources().getString(R.string.waiting_queue)+" "+tableModel.getWaitqueue());
        holder.tv_wait_time.setText(mContext.getResources().getString(R.string.time_wait)+" "+tableModel.getWaittime());


        holder.ln1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e(" Table ",""+BookFragment.Id_table);

                if(BookFragment.Id_table.isEmpty())
                {
                    holder.ln1.setBackgroundResource(R.drawable.rounded_corner_red);
                    BookFragment.Id_table = holder.id_table;
                    BookFragment.type_table = holder.tv_table.getText().toString();
                    ln_temp = holder.ln1;
                }
                else
                {
                    ln_temp.setBackgroundResource(R.drawable.rounded_corner_no);
                    holder.ln1.setBackgroundResource(R.drawable.rounded_corner_red);
                    BookFragment.Id_table = holder.id_table;
                    BookFragment.type_table = holder.tv_table.getText().toString();
                    ln_temp = holder.ln1;
                }

                /*if(BookFragment.Id_table.isEmpty())
                {
                    holder.ln1.setBackgroundResource(R.drawable.rounded_corner_red);
                    BookFragment.Id_table = holder.id_table;
                    BookFragment.type_table = holder.tv_table.getText().toString();
                }
                else if(holder.id_table.equals(BookFragment.Id_table))
                {
                    holder.ln1.setBackgroundResource(R.drawable.rounded_corner_no);
                    BookFragment.Id_table = "";
                    BookFragment.type_table = "";
                }
                */

            }
        });

    }

    @Override
    public int getItemCount() {
        return tablelist.size();
    }
}
