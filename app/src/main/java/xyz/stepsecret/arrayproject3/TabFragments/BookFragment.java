package xyz.stepsecret.arrayproject3.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import xyz.stepsecret.arrayproject3.R;


public class BookFragment extends Fragment {

    private LinearLayout ln1;
    private LinearLayout ln2;
    private LinearLayout ln3;

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

        ln1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ln1.setBackgroundResource(R.drawable.rounded_corner_red);
                ln2.setBackgroundResource(R.drawable.rounded_corner_no);
                ln3.setBackgroundResource(R.drawable.rounded_corner_no);

            }
        });

        ln2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ln1.setBackgroundResource(R.drawable.rounded_corner_no);
                ln2.setBackgroundResource(R.drawable.rounded_corner_red);
                ln3.setBackgroundResource(R.drawable.rounded_corner_no);

            }
        });

        ln3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ln1.setBackgroundResource(R.drawable.rounded_corner_no);
                ln2.setBackgroundResource(R.drawable.rounded_corner_no);
                ln3.setBackgroundResource(R.drawable.rounded_corner_red);

            }
        });

        return v;
    }

}
