package chris.example.com.skyhook.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class MapViewmodel extends ViewModel
{
    private String addresses = "";
    
    private LatLng latLng;
    
    private float zoom;
    
    public String getAddresses()
    {
        return addresses;
    }
    
    public void setAddresses(String addresses)
    {
        this.addresses = addresses;
    }
    
    public LatLng getLatLng()
    {
        return latLng;
    }
    
    public void setLatLng(LatLng latLng)
    {
        this.latLng = latLng;
    }
    
    public float getZoom()
    {
        return zoom;
    }
    
    public void setZoom(float zoom)
    {
        this.zoom = zoom;
    }
}
