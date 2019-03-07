package chris.example.com.skyhook.model.response;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference = "http://skyhookwireless.com/wps/2005")
@Root(name = "RemoteGeoRS")
public class RGEOResponse
{
    @Attribute(name = "version")
    private String version = "2.26";
    
    @Element(name = "street-address", required = false)
    private StreetAddress streetAddress;
    
    @Element(name = "error", required = false)
    private String error;
    
    public StreetAddress getStreetAddress()
    {
        return streetAddress;
    }
    
    public void setStreetAddress(StreetAddress streetAddress)
    {
        this.streetAddress = streetAddress;
    }
    
    public String getError()
    {
        return error;
    }
    
    public void setError(String error)
    {
        this.error = error;
    }
    
    @Override
    public String toString()
    {
        return "RGEOResponse{" +
                "version='" + version + '\'' +
                ", streetAddress=" + streetAddress +
                '}';
    }
}
