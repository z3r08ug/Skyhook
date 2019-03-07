package chris.example.com.skyhook.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
        
        addresses = mapViewmodel.getAddresses();
        numOutgoing = 0;
        zoom = mapViewmodel.getZoom();
        latLng = mapViewmodel.getLatLng();
        
        tvNum = findViewById(R.id.tvNum);
        tvAddresses = findViewById(R.id.tvAddresses);
        tvAddresses.setText(mapViewmodel.getAddresses());
    
        tvNum.setText(String.valueOf(numOutgoing));
    
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    
    @Override
    public void showError(String error)
    {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "showError: Error: " + error);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mapViewmodel.setZoom(googleMap.getCameraPosition().zoom);
        mapViewmodel.setAddresses(addresses);
        mapViewmodel.setLatLng(googleMap.getCameraPosition().target);
        presenter.detachView();
    }
    
    @Override
    public void setAddress(RGEOResponse rgeoResponse)
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
        tvAddresses.setText(addresses);
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
                    Log.d(TAG, "onRequestPermissionsResult: denied");
                    checkPermission();
                }
                return;
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
        }
    }
}
