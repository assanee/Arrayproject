package xyz.stepsecret.arrayproject3.TabFragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.ChangeFavorite_API;
import xyz.stepsecret.arrayproject3.API.Favorite_API;
import xyz.stepsecret.arrayproject3.API.History_API;
import xyz.stepsecret.arrayproject3.API.Login_API;
import xyz.stepsecret.arrayproject3.API.Promotion_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Model.Favorite_Model;
import xyz.stepsecret.arrayproject3.Model.History_Model;
import xyz.stepsecret.arrayproject3.Model.Login_Model;
import xyz.stepsecret.arrayproject3.Model.Promotion_Model;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.HomeRecyclerViewDataAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.models.HomeSectionDataModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.HomeSingleItemModel;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ArrayList<HomeSectionDataModel> allSampleData;
    private HomeRecyclerViewDataAdapter adapter;
    private RecyclerView my_recycler_view;
    private RestAdapter restAdapter;

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private TinyDB Store_data;

    private Boolean check_do = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.home_fragment, container, false);

        allSampleData = new ArrayList<HomeSectionDataModel>();

        Store_data = new TinyDB(getContext());

        my_recycler_view = (RecyclerView) v.findViewById(R.id.home_my_recycler_view);


        my_recycler_view.setHasFixedSize(true);


        adapter = new HomeRecyclerViewDataAdapter(getContext(), allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        my_recycler_view.setAdapter(adapter);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();

        check_do = true;

        clearData();

        buildGoogleApiClient();

        return v;
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }


    public void clearData() {
        allSampleData.clear(); //clear list
        adapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }


    public void FavoriteData()
    {

        final Favorite_API favorite_api = restAdapter.create(Favorite_API.class);

        favorite_api.Get_Favorite_API(Store_data.getString("api_key"), new Callback<Favorite_Model>() {
            @Override
            public void success(Favorite_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0) {


                    String[][] TempData = result.getData();

                    HomeSectionDataModel dm = new HomeSectionDataModel();

                    dm.setHeaderTitle(getResources().getString(R.string.favorite));
                    //dm.setHeaderTitle("Favorite");

                    ArrayList<HomeSingleItemModel> singleItem = new ArrayList<HomeSingleItemModel>();

                       for (int i = 0; i < TempData.length; i++)
                       {

                           String id = TempData[i][0];
                           String id_branch = TempData[i][4];
                           String logo = TempData[i][1];
                           String name_brand = TempData[i][2];
                           String name_branch = TempData[i][3];

                           singleItem.add(new HomeSingleItemModel(id, id_branch, name_brand, name_branch, ConfigData.Logo+logo, "normal" ));

                       }

                    dm.setAllItemsInSection(singleItem);

                    allSampleData.add(dm);
                    adapter.notifyDataSetChanged();

                    HistoryData();

                }
                else
                {
                    //show_failure(result.getMessage());
                    Log.e(" FavoriteData ","error");
                }



            }

            @Override
            public void failure(RetrofitError error) {

                show_failure(error.getMessage());
                Log.e(" FavoriteData ","failure");

            }
        });




    }

    public void HistoryData()
    {

        final History_API history_api = restAdapter.create(History_API.class);

        history_api.Get_History_API(Store_data.getString("api_key"), new Callback<History_Model>() {
            @Override
            public void success(History_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0) {


                    String[][] TempData = result.getData();

                    HomeSectionDataModel dm = new HomeSectionDataModel();

                    dm.setHeaderTitle(getResources().getString(R.string.history));
                    //dm.setHeaderTitle("History");

                    ArrayList<HomeSingleItemModel> singleItem = new ArrayList<HomeSingleItemModel>();

                    for (int i = 0; i < TempData.length; i++)
                    {

                        String id = TempData[i][0];
                        String id_branch = TempData[i][4];
                        String logo = TempData[i][1];
                        String name_brand = TempData[i][2];
                        String name_branch = TempData[i][3];

                        singleItem.add(new HomeSingleItemModel(id, id_branch, name_brand, name_branch, ConfigData.Logo+logo, "normal" ));

                    }

                    dm.setAllItemsInSection(singleItem);

                    allSampleData.add(dm);
                    adapter.notifyDataSetChanged();


                }
                else
                {
                    //show_failure(result.getMessage());
                    Log.e(" HistoryData ","error");
                }



            }

            @Override
            public void failure(RetrofitError error) {

                show_failure(error.getMessage());
                Log.e(" HistoryData ","failure");

            }
        });


    }

    public void PromotionData(Location location)
    {

        Log.e("Promotion home "," lat : "+location.getLatitude()+" long : "+location.getLongitude());

        final Promotion_API promotion_api = restAdapter.create(Promotion_API.class);

        promotion_api.Get_Promotion_API(Store_data.getString("api_key"),location.getLatitude()+"",location.getLongitude()+"", new Callback<Promotion_Model>() {
            @Override
            public void success(Promotion_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0) {

                    String[][] TempData = result.getData();

                    HomeSectionDataModel dm = new HomeSectionDataModel();

                    dm.setHeaderTitle(getResources().getString(R.string.promotion));

                    ArrayList<HomeSingleItemModel> singleItem = new ArrayList<HomeSingleItemModel>();

                    for (int i = 0; i < TempData.length; i++)
                    {

                        String id = TempData[i][0];
                        String id_branch = TempData[i][1];
                        String logo = TempData[i][4];
                        String name_brand = TempData[i][2];
                        String name_branch = TempData[i][3];

                        singleItem.add(new HomeSingleItemModel(id, id_branch, name_brand, name_branch, ConfigData.Promotion+logo, "promotion" ));

                    }

                    dm.setAllItemsInSection(singleItem);

                    allSampleData.add(dm);
                    adapter.notifyDataSetChanged();

                }
                else
                {
                   // show_failure(result.getMessage());
                    Log.e(" PromotionData ","error");
                }



            }

            @Override
            public void failure(RetrofitError error) {

                show_failure(error.getMessage());
                Log.e(" PromotionData ","failure ");

            }
        });


    }


    public void show_failure(String message)
    {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }



    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {

            mGoogleApiClient.connect();
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();


    }

    @Override
    public void onResume() {
        super.onResume();




    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if(check_do && mLastLocation != null)
        {   check_do = false;

            FavoriteData();

            Log.e("Promotion home "," lat : "+mLastLocation.getLatitude()+" long : "+mLastLocation.getLongitude());
            PromotionData(mLastLocation);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(" Home ", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());


    }


}
