package chris.example.com.skyhook.model.request;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root (name = "ReverseGeoRQ")
@Namespace (reference = "http://skyhookwireless.com/wps/2005")
public class ReverseGeoRQ
{
    @Element(name = "authentication")
    private Authentication authentication;
    
    @Attribute(name = "version")
    private String  version = "2.8";
    
    @Attribute(name = "street-address-lookup")
    private String sal = "full";
    
    @Element(name = "point")
    private Point point;
    
    public Authentication getAuthentication()
    {
        return authentication;
    }
    
    public void setAuthentication(Authentication authentication)
    {
        this.authentication = authentication;
    }
    
    public Point getPoint()
    {
        return point;
    }
    
    public void setPoint(Point point)
    {
        this.point = point;
    }
    
    @Override
    public String toString()
    {
        return "ReverseGeoRQ{" +
                "authentication=" + authentication +
                ", version='" + version + '\'' +
                ", sal='" + sal + '\'' +
                ", point=" + point +
                '}';
    }
}
