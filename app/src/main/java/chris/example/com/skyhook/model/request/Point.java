package chris.example.com.skyhook.model.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "point")
public class Point
{
    @Element(name = "latitude")
    private String lat;
    
    @Element(name = "longitude")
    private String lon;
    
    public String getLat()
    {
        return lat;
    }
    
    public void setLat(String lat)
    {
        this.lat = lat;
    }
    
    public String getLon()
    {
        return lon;
    }
    
    public void setLon(String lon)
    {
        this.lon = lon;
    }
    
    @Override
    public String toString()
    {
        return "Point{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
}
