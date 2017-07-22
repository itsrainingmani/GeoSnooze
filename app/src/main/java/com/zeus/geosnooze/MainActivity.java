package com.zeus.geosnooze;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.instantapps.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Text;

public class MainActivity extends FragmentActivity implements View.OnClickListener, GoogleMap.OnMapClickListener, OnMapReadyCallback{

    protected  static final String TAG = "MainActivity";
    private GoogleMap gMap;
    private Button locate, set, reset;
    private boolean mRequestingLocationUpdates;
    private String REQUEST_LOCATIONS_UPDATES_KEY = "geosnooze";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationSettingsRequest.Builder builder;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private TextView latfield;
    private TextView longfield;

    private void initUI(){
        locate = (Button) findViewById(R.id.locate);
        set = (Button) findViewById(R.id.set);
        reset = (Button) findViewById(R.id.reset);
        locate.setOnClickListener(this);
        set.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
//        mapFragment.getMapAsync(this);
//        latfield = (TextView) findViewById(R.id.latitudeView);
//        longfield = (TextView) findViewById(R.id.longitudeView);

//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
//
//        SettingsClient client = LocationServices.getSettingsClient(this);
//        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//
//        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//            @Override
//            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                // All location settings are satisfied. Client can initialize location requests
//            }
//        });
//
//        task.addOnFailureListener(this, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                int statusCode = ((ApiException) e).getStatusCode();
//                switch (statusCode) {
//                    case CommonStatusCodes.RESOLUTION_REQUIRED:
//                        try {
//                            ResolvableApiException resolvable = (ResolvableApiException) e;
//                            resolvable.startResolutionForResult(MainActivity.this, REQUEST_CODE_ASK_PERMISSIONS);
//                        } catch (IntentSender.SendIntentException sendEx) {
//
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        break;
//                }
//            }
//        });

//        mLocationCallback = new LocationCallback(){
//            public void onLocationResult(LocationResult locationResult){
//                for (Location location: locationResult.getLocations()){
//
//                }
//            }
//        };

//        final Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (android.support.v4.app.ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                        showMessageOkCancel("You need location services for this app", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                android.support.v4.app.ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
//                            }
//                        });
//                    } else {
//                        android.support.v4.app.ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
//                    }
//                }
//                Task<Location> locationTask = mFusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            latfield.setText(Double.toString(location.getLatitude()));
//                            longfield.setText(Double.toString(location.getLongitude()));
//                        }
//                    }
//                });
//                mLocationCallback = new LocationCallback(){
//                    public void onLocationResult(LocationResult locationResult){
//                        for (Location location: locationResult.getLocations()){
//                            if (location != null) {
//                                latfield.setText(Double.toString(location.getLatitude()));
//                                longfield.setText(Double.toString(location.getLongitude()));
//                            }
//                        }
//                    }
//                };
//            }
//        });
//        updateValuesFromBundle(savedInstanceState);
    }

    @Override
    public void onClick(View v){}

    @Override
    public void onMapReady(GoogleMap map){
        if (gMap == null){
            gMap = map;
            setUpMap();
        }
        LatLng shenzhen = new LatLng(22.5362, 113.9454);
        gMap.addMarker(new MarkerOptions().position(shenzhen).title("Marker in Shenzhen"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(shenzhen));
    }

    private void setUpMap(){
        gMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng point){

    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(REQUEST_LOCATIONS_UPDATES_KEY, mRequestingLocationUpdates);
        super.onSaveInstanceState(outState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState){
        if (savedInstanceState.keySet().contains(REQUEST_LOCATIONS_UPDATES_KEY)){
            mRequestingLocationUpdates = savedInstanceState.getBoolean(REQUEST_LOCATIONS_UPDATES_KEY);
        }
    }

    protected void OnResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    protected void onPause(){
        super.onPause();
        stopLocationUpdates();
    }

    protected void OnDestroy(){
        super.onDestroy();
    }

    public void onReturn(View view){
        Log.d(TAG, "onReturn");
        this.finish();
    }

    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void startLocationUpdates() {
        if (android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void showMessageOkCancel(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(MainActivity.this).setMessage(message).setPositiveButton("OK", okListener).setNegativeButton("Cancel", null).create().show();
    }

    protected void createLocationRequest(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}
