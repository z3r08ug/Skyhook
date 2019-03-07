package chris.example.com.skyhook.model.request;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "authentication")
public class Authentication
{
    @Attribute(name = "version")
    private String  version = "2.2";
    
    @Element(name = "key")
    private Key key;
    
    public Key getKey()
    {
        return key;
    }
    
    public void setKey(Key key)
    {
        this.key = key;
    }
    
    @Override
    public String toString()
    {
        return "Authentication{" +
                "version='" + version + '\'' +
                ", key=" + key +
                '}';
    }
}
