package xyz.stepsecret.arrayproject3;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

/**
 * Created by stepsecret on 14/8/2559.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMarkerClickListener,  GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        LocationListener {

    private Toolbar toolbar;

    private SearchView search_shop;

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

        search_shop = (SearchView) findViewById(R.id.search_shop);
        search_shop.setQueryHint(getResources().getString(R.string.shop_search));

        search_shop.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });


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

            //Log.e(" Map ","lat : "+mLastLocation.getLatitude());
            //Log.e(" Map ","long : "+mLastLocation.getLongitude());

            setCamera();
            //setMeMark();

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

        String pos = mHashMap.get(marker);

        Log.e(" Shop ","onMarkerClick "+pos);

        return false;
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

}
