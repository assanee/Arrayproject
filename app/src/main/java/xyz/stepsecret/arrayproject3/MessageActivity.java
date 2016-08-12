package xyz.stepsecret.arrayproject3;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import xyz.stepsecret.arrayproject3.Badge.Utils;
import xyz.stepsecret.arrayproject3.TabFragments.DividerItemDecoration;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.AllPromotionAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.MessageAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.MessageModel;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

/**
 * Created by stepsecret on 12/8/2559.
 */
public class MessageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView img_message, img_delete;

    private TinyDB Store_data;

    private List<MessageModel> MessageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Message");

        Store_data = new TinyDB(getApplicationContext());

        img_message = (ImageView) findViewById(R.id.img_message);
        img_delete = (ImageView) findViewById(R.id.img_delete);




        Glide.with(this)
                .load(R.drawable.chat)
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_message);

        Glide.with(this)
                .load(R.drawable.deletebutton)
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_delete);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //img_brand = (ImageView) v.findViewById(R.id.img_brand);
        //tv_brand = (TextView) v.findViewById(R.id.tv_name_brand);

        mAdapter = new MessageAdapter(this,MessageList);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MessageModel Messages = MessageList.get(position);
                Toast.makeText(getApplicationContext(),  Messages.getId() + " is selected!", Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(getApplicationContext(), PromotionDetail.class);
                //startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        clearData();
        prepareMovieData();


    }

    public void clearData() {
        MessageList.clear(); //clear list
        mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }


    private void prepareMovieData() {



        String message = "Test messag emessag emes sagemes sagem essage messag emess ageme ssagemessa gemessage";
        //String message = "Test message";
        String date = "05/12/2016";
        String time = "12:30:22";
        Boolean status = true;

        MessageModel Messages;
        for(int i = 0 ; i < 5 ; i++)
        {
            Messages = new MessageModel(i+"", message, date, time, status);
            MessageList.add(Messages);

        }
        status = false;
        for(int i = 0 ; i < 5 ; i++)
        {
            Messages = new MessageModel(i+"", message, date, time, status);
            MessageList.add(Messages);

        }




        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem control = menu.findItem(R.id.action_control);
        MenuItem mail = menu.findItem(R.id.action_mail);

        // Obtener drawable del item
        LayerDrawable control_icon = (LayerDrawable) control.getIcon();
        LayerDrawable mail_icon = (LayerDrawable) mail.getIcon();

        // Actualizar el contador

        //Utils.setBadgeCount(this, control_icon, 3);
        //Utils.setBadgeCount(this, mail_icon, 3);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_control) {
            Log.e(" menu "," control ");

            LayerDrawable control_icon = (LayerDrawable) item.getIcon();

            Utils.setBadgeCount(this, control_icon, 0);

            Intent intent = new Intent(this, ControlActivity.class);
            startActivity(intent);
            finish();


            return true;
        }
        else if (id == R.id.action_mail) {
            Log.e(" menu "," mail ");

            LayerDrawable mail_icon = (LayerDrawable) item.getIcon();
            Utils.setBadgeCount(this, mail_icon, 0);

            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);
            finish();

            return true;
        }
        else
        {
            finish();

            return true;
        }

        //return super.onOptionsItemSelected(item);
    }
}

