package xyz.stepsecret.arrayproject3.TabFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.QueueAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.QueueModel;

import java.util.ArrayList;
import java.util.List;


public class QueueFragment extends Fragment {

    private List<QueueModel> queueList = new ArrayList<>();
    private RecyclerView recyclerView;
    private QueueAdapter mAdapter;
    //SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View v = inflater.inflate(R.layout.queue_content, container, false);


        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
       // mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swifeRefresh);
       // mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        mAdapter = new QueueAdapter(getContext(),queueList);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                QueueModel queues = queueList.get(position);
                //Toast.makeText(getContext(), queues.getTime() + " is selected!", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

      /*  mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                prepareMovieData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
*/
        clearData();
        prepareMovieData();

        return v;
    }
    public void clearData() {
        queueList.clear(); //clear list
        mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }


    private void prepareMovieData() {

       // mAdapter.re

        String[] alert = new String[]{
                "05/12/2016 12:32:22 A010 Your Queue is on service.",
                "05/12/2016 12:26:26 A010 5 Queue to go." ,
                "05/12/2016 12:01:13 Thank you for registed. You Queue is A010 - 6 waiting queue (30 min)"};

        QueueModel queues = new QueueModel(alert,"1-2", "Xeuslab", "branch", "A009", "3", "30", "9", "Assanee", "Saksiritantawan", "5/12/2016", "12:00:00", "A010", "200", "http://stepsecret.xyz/standingupman.png");
        queueList.add(queues);

  /*    queues = new QueueModel("1-2", "Xeuslab", "A009", "3", "30", "Assanee", "Saksiritantawan", "5/12/2016", "12:00:00", "A010", "200", "http://stepsecret.xyz/standingupman.png");
        queueList.add(queues);

        queues = new QueueModel("1-2", "Xeuslab", "A009", "3", "30", "Assanee", "Saksiritantawan", "5/12/2016", "12:00:00", "A010", "200", "http://stepsecret.xyz/standingupman.png");
        queueList.add(queues);

        queues = new QueueModel("Assanee", "Saksiritantawan", "5/12/2016", "12:00:00", "A010", "2", "http://stepsecret.xyz/standingupman.png");
        queueList.add(queues);

        queues = new QueueModel("Assanee", "Saksiritantawan", "5/12/2016", "12:00:00", "A010", "2", "http://stepsecret.xyz/standingupman.png");
        queueList.add(queues);

        queues = new QueueModel("Assanee", "Saksiritantawan", "5/12/2016", "12:00:00", "A010", "2", "http://stepsecret.xyz/standingupman.png");
        queueList.add(queues);

        queues = new QueueModel("Assanee", "Saksiritantawan", "5/12/2016", "12:00:00", "A010", "2", "http://stepsecret.xyz/standingupman.png");
        queueList.add(queues);




        queues = new QueueModel("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        queueList.add(queues);
    */
        mAdapter.notifyDataSetChanged();
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
