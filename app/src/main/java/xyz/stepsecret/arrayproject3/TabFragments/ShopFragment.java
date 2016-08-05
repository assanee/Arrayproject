package xyz.stepsecret.arrayproject3.TabFragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.ShopRecyclerViewDataAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSectionDataModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSingleItemModel;

/**
 * Created by iFocus on 27-10-2015.
 */
public class ShopFragment extends Fragment implements OnMapReadyCallback , GoogleMap.OnMyLocationButtonClickListener {

    public GoogleMap gMap;
    public MapView mMap;
    ArrayList<ShopSectionDataModel> allSampleData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.shop_fragment, container, false);


        mMap = (MapView) v.findViewById(R.id.mapView);
        mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(this);



        allSampleData = new ArrayList<ShopSectionDataModel>();
        RecyclerView my_recycler_view = (RecyclerView) v.findViewById(R.id.shop_my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        ShopRecyclerViewDataAdapter adapter = new ShopRecyclerViewDataAdapter(getContext(), allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        my_recycler_view.setAdapter(adapter);

        createFavorite();
        createNear();

        return v;
    }

    public void createFavorite() {


            ShopSectionDataModel dm = new ShopSectionDataModel();

            dm.setHeaderTitle("Favorite");

            ArrayList<ShopSingleItemModel> singleItem = new ArrayList<ShopSingleItemModel>();
            for (int j = 0; j <= 10; j++) {

                singleItem.add(new ShopSingleItemModel("id = ","XeusXeus1" , "Branch" , "http://www.exotictheme.com/wp-content/uploads/2015/11/food-logo.png"));
            }

            dm.setAllItemsInSection(singleItem);


            allSampleData.add(dm);



    }
    public void createNear() {


            ShopSectionDataModel dm = new ShopSectionDataModel();

            dm.setHeaderTitle("List shop nearby");

            ArrayList<ShopSingleItemModel> singleItem = new ArrayList<ShopSingleItemModel>();
            for (int j = 0; j <= 10; j++) {

                singleItem.add(new ShopSingleItemModel("id = ","XeusXeus1XeusXeus1XeusXeus1" , "Branch" , "http://www.exotictheme.com/wp-content/uploads/2015/11/food-logo.png"));
            }

            dm.setAllItemsInSection(singleItem);


            allSampleData.add(dm);



    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();



    }

    @Override
    public void onPause() {
        super.onPause();
        mMap.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMap.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Do something with Google Map

        MapsInitializer.initialize(getContext());
        gMap = googleMap;
        gMap.setOnMyLocationButtonClickListener(this);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }



        Log.e(" Shop ","onMapReady");
    }

    @Override
    public boolean onMyLocationButtonClick() {

        Log.e(" Shop ","onMyLocationButtonClick");
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

}
