package xyz.stepsecret.arrayproject3.TabFragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import xyz.stepsecret.arrayproject3.API.Favorite_API;
import xyz.stepsecret.arrayproject3.API.Near_API;
import xyz.stepsecret.arrayproject3.API.Promotion_API;
import xyz.stepsecret.arrayproject3.API.SearchNear_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.MainActivity;
import xyz.stepsecret.arrayproject3.MapActivity;
import xyz.stepsecret.arrayproject3.Model.Favorite_Model;
import xyz.stepsecret.arrayproject3.Model.Near_Model;
import xyz.stepsecret.arrayproject3.Model.Promotion_Model;
import xyz.stepsecret.arrayproject3.Model.SearchNear_Model;
import xyz.stepsecret.arrayproject3.R;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.ShopRecyclerViewDataAdapter;
import xyz.stepsecret.arrayproject3.TabFragments.adapters.ShopRecyclerViewDataAdapterSearch;
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSectionDataModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSingleItemModel;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

/**
 * Created by stepsecret on 27-10-2015.
 */
public class ShopFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


    private ArrayList<ShopSectionDataModel> allSampleData;
    private ArrayList<ShopSectionDataModel> allSampleDataSearch;

    private SearchView search_shop;
    private RecyclerView my_recycler_view;
    private RecyclerView my_recycler_view_search;
    private ShopRecyclerViewDataAdapter adapter;
    private ShopRecyclerViewDataAdapterSearch adapter_search;
    private ImageView img_map;

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
        View v = inflater.inflate(R.layout.shop_fragment, container, false);

        Store_data = new TinyDB(getContext());

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();

        allSampleData = new ArrayList<ShopSectionDataModel>();
        allSampleDataSearch = new ArrayList<ShopSectionDataModel>();
        search_shop = (SearchView) v.findViewById(R.id.search_shop);

        img_map = (ImageView) v.findViewById(R.id.img_map);

        my_recycler_view = (RecyclerView) v.findViewById(R.id.shop_my_recycler_view);
        my_recycler_view_search = (RecyclerView) v.findViewById(R.id.shop_my_recycler_view_search);



        my_recycler_view.setHasFixedSize(true);
        my_recycler_view_search.setHasFixedSize(true);
        adapter = new ShopRecyclerViewDataAdapter(getContext(), allSampleData);
        adapter_search = new ShopRecyclerViewDataAdapterSearch(getContext(), allSampleDataSearch);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        my_recycler_view_search.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);
        my_recycler_view_search.setAdapter(adapter_search);

        /*my_recycler_view_search.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        my_recycler_view_search.setLayoutManager(mLayoutManager);
        my_recycler_view_search.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        my_recycler_view_search.setItemAnimator(new DefaultItemAnimator());
        my_recycler_view_search.setAdapter(adapter_search);
        */

        my_recycler_view_search.setVisibility(View.GONE);


        search_shop.setQueryHint(getResources().getString(R.string.shop_search));

        search_shop.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getContext(), query, Toast.LENGTH_LONG).show();
                //search_branch.setVisibility(View.VISIBLE);

                if(!query.isEmpty())
                {
                    SearchNearData(query);
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getContext(), newText, Toast.LENGTH_LONG).show();
                if(!newText.isEmpty())
                {
                    SearchNearData(newText);
                }
                return false;
            }
        });



        Glide.with(this)
                .load(R.drawable.map)
                .placeholder(R.drawable.nodownload)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_map);



        img_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
                //finish();

            }
        });

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

        Log.e("Shop "," Clear Data ");
        allSampleData.clear(); //clear list
        adapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

    public void clearDataSearch()
    {
        Log.e("Shop "," Search Clear Data ");
        allSampleDataSearch.clear(); //clear list
        adapter_search.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

    public void FavoriteData()
    {

        final Favorite_API favorite_api = restAdapter.create(Favorite_API.class);

        favorite_api.Get_Favorite_API(Store_data.getString("api_key"), new Callback<Favorite_Model>() {
            @Override
            public void success(Favorite_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0) {

                    Log.e(" TAG ","success : favorite : > "+result.getData().length );

                    String[][] TempData = result.getData();

                    ShopSectionDataModel dm = new ShopSectionDataModel();

                    dm.setHeaderTitle(getResources().getString(R.string.favorite));
                    //dm.setHeaderTitle("Favorite");

                    ArrayList<ShopSingleItemModel> singleItem = new ArrayList<ShopSingleItemModel>();

                    for (int i = 0; i < TempData.length; i++)
                    {

                        String id = TempData[i][0];
                        String id_branch = TempData[i][4];
                        String logo = TempData[i][1];
                        String name_brand = TempData[i][2];
                        String name_branch = TempData[i][3];

                        singleItem.add(new ShopSingleItemModel(id, id_branch, name_brand, name_branch, ConfigData.Logo+logo));

                    }

                    dm.setAllItemsInSection(singleItem);

                    allSampleData.add(dm);
                    adapter.notifyDataSetChanged();



                }
                else
                {
                    show_failure(result.getMessage());
                    Log.e(" shop FavoriteData ","error");
                }



            }

            @Override
            public void failure(RetrofitError error) {

                show_failure(error.getMessage());
                Log.e(" shop FavoriteData ","failure");

            }
        });




    }

    public void NearData(Location location)
    {

        Log.e(" NearData ","start > "+location.getLatitude()+" : "+location.getLongitude());

        final Near_API near_api = restAdapter.create(Near_API.class);

        near_api.Get_NEAR_API(Store_data.getString("api_key"),location.getLatitude()+"",location.getLongitude()+"", new Callback<Near_Model>() {
            @Override
            public void success(Near_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0) {

                    Log.e(" TAG ","success : history : > "+result.getData().length );

                    String[][] TempData = result.getData();

                    ShopSectionDataModel dm = new ShopSectionDataModel();

                    dm.setHeaderTitle(getResources().getString(R.string.near));

                    ArrayList<ShopSingleItemModel> singleItem = new ArrayList<ShopSingleItemModel>();

                    for (int i = 0; i < TempData.length; i++)
                    {

                        String id = TempData[i][0];
                        String id_branch = TempData[i][1];
                        String logo = TempData[i][9];
                        String name_brand = TempData[i][2];
                        String name_branch = TempData[i][3];

                        //Log.e(" Home ",""+ConfigData.Promotion+logo);

                        singleItem.add(new ShopSingleItemModel(id, id_branch, name_brand, name_branch, ConfigData.Logo+logo));

                    }

                    dm.setAllItemsInSection(singleItem);

                    allSampleData.add(dm);
                    adapter.notifyDataSetChanged();

                }
                else
                {
                    //show_failure(result.getMessage());
                    Log.e(" NearData ","error");
                }



            }

            @Override
            public void failure(RetrofitError error) {

                show_failure(error.getMessage());
                Log.e(" NearData ","failure ");

            }
        });


    }


    public void SearchNearData(final String query)
    {
        if(mLastLocation != null)
        {
            Log.e(" NearData ","start > "+mLastLocation.getLatitude()+" : "+mLastLocation.getLongitude());

            final SearchNear_API searchNear_api = restAdapter.create(SearchNear_API.class);

            searchNear_api.Get_Search_API(Store_data.getString("api_key"), query, mLastLocation.getLatitude()+"", mLastLocation.getLongitude()+"", new Callback<SearchNear_Model>() {
                @Override
                public void success(SearchNear_Model result, Response response) {

                    if(!result.getError() && result.getData().length > 0) {

                        clearDataSearch();
                        my_recycler_view.setVisibility(View.GONE);
                        my_recycler_view_search.setVisibility(View.VISIBLE);

                        Log.e(" TAG ","success : history : > "+result.getData().length );

                        String[][] TempData = result.getData();

                        ShopSectionDataModel dm = new ShopSectionDataModel();

                        dm.setHeaderTitle(getResources().getString(R.string.search)+" : "+query);

                        ArrayList<ShopSingleItemModel> singleItem = new ArrayList<ShopSingleItemModel>();

                        for (int i = 0; i < TempData.length; i++)
                        {

                            String id = TempData[i][0];
                            String id_branch = TempData[i][1];
                            String logo = TempData[i][9];
                            String name_brand = TempData[i][2];
                            String name_branch = TempData[i][3];

                            Log.e(" Shop ","name_brand : "+name_brand);

                            singleItem.add(new ShopSingleItemModel(id, id_branch, name_brand, name_branch, ConfigData.Logo+logo));

                        }

                        dm.setAllItemsInSection(singleItem);

                        allSampleDataSearch.add(dm);
                        adapter_search.notifyDataSetChanged();

                    }
                    else
                    {
                        my_recycler_view_search.setVisibility(View.GONE);
                        Log.e(" NearData ","error");
                    }



                }

                @Override
                public void failure(RetrofitError error) {

                    show_failure(error.getMessage());
                    Log.e(" NearData ","failure ");

                }
            });
        }




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
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        Log.e(" Home "," Location success ");

        if(check_do && mLastLocation != null)
        {
            check_do = false;

            FavoriteData();

            NearData(mLastLocation);
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
