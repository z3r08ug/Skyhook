package chris.example.com.skyhook.model.response;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "street-address")
public class StreetAddress
{
    @Attribute(name = "distanceToPoint", required = false)
    private String distance;
    
    @Element(name = "street-number", required = false)
    private String streetNum;
    
    @Element(name = "address-line", required = false)
    private String addressLine;
    
    @Element(name = "metro1", required = false)
    private String metro1;
    
    @Element(name = "metro2", required = false)
    private String metro2;
    
    @Element(name = "city", required = false)
    private String city;
    
    @Element(name = "postal-code", required = false)
    private String postalCode;
    
    @Element(name = "county", required = false)
    private String county;
    
    @Element(name = "state", required = false)
    private String state;
    
    @Element(name = "country")
    private String country;
    
    public String getDistance()
    {
        return distance;
    }
    
    public void setDistance(String distance)
    {
        this.distance = distance;
    }
    
    public String getStreetNum()
    {
        return streetNum;
    }
    
    public void setStreetNum(String streetNum)
    {
        this.streetNum = streetNum;
    }
    
    public String getAddressLine()
    {
        return addressLine;
    }
    
    public void setAddressLine(String addressLine)
    {
        this.addressLine = addressLine;
    }
    
    public String getMetro1()
    {
        return metro1;
    }
    
    public void setMetro1(String metro1)
    {
        this.metro1 = metro1;
    }
    
    public String getMetro2()
    {
        return metro2;
    }
    
    public void setMetro2(String metro2)
    {
        this.metro2 = metro2;
    }
    
    public String getPostalCode()
    {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }
    
    public String getCounty()
    {
        return county;
    }
    
    public void setCounty(String county)
    {
        this.county = county;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    @Override
    public String toString()
    {
        return "StreetAddress{" +
                "distance='" + distance + '\'' +
                ", streetNum='" + streetNum + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", metro1='" + metro1 + '\'' +
                ", metro2='" + metro2 + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", county='" + county + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
