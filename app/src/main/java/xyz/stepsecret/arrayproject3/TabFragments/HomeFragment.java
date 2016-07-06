package xyz.stepsecret.arrayproject3.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.stepsecret.arrayproject3.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerview;
    private RecyclerView recyclerview2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.home_fragment, container, false);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerview.setLayoutManager(llm);

        recyclerview2 = (RecyclerView)view.findViewById(R.id.recyclerview2);
        StaggeredGridLayoutManager llm2 = new StaggeredGridLayoutManager(1,1);
        recyclerview2.setLayoutManager(llm2);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            items.add("TextView_"+i);
        }

        HomeAdapter adapter = new HomeAdapter(items);
        HomeAdapter2 adapter2 = new HomeAdapter2(items);
        recyclerview.setAdapter(adapter);
        recyclerview2.setAdapter(adapter2);

    }

}
