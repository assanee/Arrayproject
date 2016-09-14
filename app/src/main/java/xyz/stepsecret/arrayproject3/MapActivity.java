package xyz.stepsecret.arrayproject3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.arrayproject3.API.Near_API;
import xyz.stepsecret.arrayproject3.Config.ConfigData;
import xyz.stepsecret.arrayproject3.Model.Near_Model;
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSectionDataModel;
import xyz.stepsecret.arrayproject3.TabFragments.models.ShopSingleItemModel;
import xyz.stepsecret.arrayproject3.TinyDB.TinyDB;

/**
 * Created by stepsecret on 14/8/2559.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMarkerClickListener,  GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        LocationListener {

    private Toolbar toolbar;

    public GoogleMap gMap;
    public MapView mMap;

    private Location mLastLocation;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private GoogleApiClient mGoogleApiClient;

    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    public static Marker Your_Marker;
    public static Marker[] ST_Marker;
    public static LatLng camera;

    private HashMap<Marker, String> mHashMap = new HashMap<Marker, String>();

    private RestAdapter restAdapter;

    private TinyDB Store_data;

    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.map));

        mMap = (MapView) findViewById(R.id.mapView);
        mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(this);


        Store_data = new TinyDB(this);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConfigData.API).build();

        buildGoogleApiClient();

        createLocationRequest();

    }


    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    /**
     * Creating location request object
     * */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }



    /**
     * Starting the location updates
     * */
    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        if (mLastLocation != null) {

            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

            setCamera();
            NearData(mLastLocation);
        }
    }

    public void setCamera()
    {
        camera = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 15));
    }

    public void setMeMark()
    {

        //mHashMap.clear();

        if(Your_Marker == null)
        {
            Your_Marker = gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.me))
                    .title("Your Here"));

            //mHashMap.put(Your_Marker, "99");
        }
        else
        {
            Your_Marker.remove();
            Your_Marker = gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.me))
                    .title("Your Here"));

           //mHashMap.put(Your_Marker, "91");

        }
    }

    public void setMarker(final String[] lat_long)
    {

        final Marker[] shop = new Marker[1];


        Glide.with(this).load(ConfigData.Logo+lat_long[9])
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(60, 60)
                .into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {

                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);

                shop[0] = gMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(lat_long[6]),Double.parseDouble(lat_long[7])))
                        .icon(icon)
                        .title(lat_long[2]+" : "+lat_long[3]));

                mHashMap.put(shop[0], lat_long[0]);

            }
        });


    }

    public void NearData(Location location)
    {

        final Near_API near_api = restAdapter.create(Near_API.class);

        near_api.Get_NEAR_API(Store_data.getString("api_key"),location.getLatitude()+"",location.getLongitude()+"", new Callback<Near_Model>() {
            @Override
            public void success(Near_Model result, Response response) {

                if(!result.getError() && result.getData().length > 0) {


                    String[][] TempData = result.getData();

                    for (int i = 0; i < TempData.length; i++)
                    {

                       // Log.e(" Map ",""+i);

                        String[] lat_long = TempData[i];

                        setMarker(lat_long);
                    }


                    pDialog.cancel();

                }
                else
                {
                    pDialog.cancel();
                    show_failure(result.getMessage());
                    Log.e(" TAG ","error");
                }



            }

            @Override
            public void failure(RetrofitError error) {

                pDialog.cancel();
                show_failure(error.getMessage());
                Log.e(" TAG ","failure ");

            }
        });


    }


    @Override
    public void onConnectionSuspended(int i) {

        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(" Map ", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

    }


    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;

        Toast.makeText(getApplicationContext(), "Location changed!",
                Toast.LENGTH_SHORT).show();

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
        mMap.onResume();

        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }

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

        MapsInitializer.initialize(this);
        gMap = googleMap;
        gMap.setOnMyLocationButtonClickListener(this);
        gMap.setOnMarkerClickListener(this);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true);




    }

    @Override
    public boolean onMyLocationButtonClick() {

        Log.e(" Shop ","onMyLocationButtonClick");
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        String id_branch = mHashMap.get(marker);

        Log.e(" Shop ","onMarkerClick "+id_branch);

        Intent intent = new Intent(this, BookBranch.class);
        intent.putExtra("id_branch", id_branch);
        startActivity(intent);

        return true;
    }

    @Override
    public void onBackPressed() {

        stopLocationUpdates();
        finish();

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        stopLocationUpdates();

        finish();
        return true;


    }


    public void show_failure(String message)
    {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }


}
