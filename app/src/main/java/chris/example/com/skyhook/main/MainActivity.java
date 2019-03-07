package chris.example.com.skyhook.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.Selection;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import chris.example.com.skyhook.R;
import chris.example.com.skyhook.application.SkyhookApplication;
import chris.example.com.skyhook.model.response.RGEOResponse;
import chris.example.com.skyhook.viewmodel.MapViewmodel;


public class MainActivity extends FragmentActivity implements MainContract.View, OnMapReadyCallback
{
    public static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 10;
    
    @Inject
    MainPresenter presenter;
    private TextView tvNum, tvAddresses;
    private GoogleMap googleMap;
    private String addresses;
    private int numOutgoing;
    private MapViewmodel mapViewmodel;
    private float zoom;
    private LatLng latLng;
    private boolean hasCoordinates;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SkyhookApplication.get(this).getMainComponent().inject(this);
    
        bindViews();
        
        presenter.attachView(this);
    }
    
    private void bindViews()
    {
        mapViewmodel = ViewModelProviders.of(this).get(MapViewmodel.class);
        hasCoordinates = false;
        
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        zoom = preferences.getFloat("zoom", -1f);
        addresses = preferences.getString("addresses", "");
        
        String latitude = preferences.getString("lat", "");
        String longitude = preferences.getString("lon", "");
        
        if (zoom == -1)
        {
            zoom = mapViewmodel.getZoom();
        }
        if (addresses.isEmpty())
        {
            addresses = mapViewmodel.getAddresses();
        }
        if (latitude.isEmpty() && longitude.isEmpty())
        {
            latLng = mapViewmodel.getLatLng();
        }
        else
        {
            hasCoordinates = true;
            latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        }
        
        numOutgoing = 0;
        
        tvNum = findViewById(R.id.tvNum);
        tvAddresses = findViewById(R.id.tvAddresses);
        tvAddresses.setMovementMethod(new ScrollingMovementMethod());
    
        SpannableString spannable = new SpannableString(addresses);
        Selection.setSelection(spannable, spannable.length());
        tvAddresses.setText(spannable, TextView.BufferType.SPANNABLE);
    
        tvNum.setText(String.valueOf(numOutgoing));
    
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
        {
            mapFragment.getMapAsync(this);
        }
    }
    
    @Override
    public void showError(String error)
    {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        numOutgoing--;
        tvNum.setText(String.valueOf(numOutgoing));
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        latLng = googleMap.getCameraPosition().target;
        zoom = googleMap.getCameraPosition().zoom;
        
        mapViewmodel.setZoom(zoom);
        mapViewmodel.setAddresses(addresses);
        mapViewmodel.setLatLng(latLng);
    
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("zoom", zoom);
        editor.putString("lat", String.valueOf(latLng.latitude));
        editor.putString("lon", String.valueOf(latLng.longitude));
        editor.putString("addresses", addresses);
        editor.apply();
    
        presenter.detachView();
    }
    
    @Override
    public void setAddress(RGEOResponse rgeoResponse)
    {
        if (rgeoResponse.getStreetAddress() != null)
        {
            String num = rgeoResponse.getStreetAddress().getStreetNum();
            if (num == null)
                num = "";
    
            String street = rgeoResponse.getStreetAddress().getAddressLine();
            if (street == null)
                street = "";
    
            String city = rgeoResponse.getStreetAddress().getCity();
            if (city == null)
                city = "";
    
            String state = rgeoResponse.getStreetAddress().getState();
            if (state == null)
                state = "";
            addresses += "*" + num + " " + street + ", " + city + ", " + state + "\n";
    
            SpannableString spannable = new SpannableString(addresses);
            Selection.setSelection(spannable, spannable.length());
            tvAddresses.setText(spannable, TextView.BufferType.SPANNABLE);
        }
        else
        {
            Toast.makeText(this, "That location does not contain a physical address...", Toast.LENGTH_SHORT).show();
        }
        numOutgoing--;
        tvNum.setText(String.valueOf(numOutgoing));
    }
    
    @Override
    public void showProgress(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        SkyhookApplication.get(this).clearMainComponent();
    }
    
    public void getCivic(View view)
    {
        numOutgoing++;
        tvNum.setText(String.valueOf(numOutgoing));
        LatLng latLng = googleMap.getCameraPosition().target;
        
        String lat = String.valueOf(latLng.latitude);
        String lon = String.valueOf(latLng.longitude);
        
        presenter.getAddress(lat, lon);
    }
    
    @Override
    public void onMapReady(GoogleMap map)
    {
        googleMap = map;
        
        checkPermission();
    }
    
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setCompassEnabled(true);
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                }
                else
                {
                    checkPermission();
                }
            }
            
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    
    private void checkPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        else
        {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
    
            if (hasCoordinates)
            {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(zoom).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }
}
