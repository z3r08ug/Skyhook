package chris.example.com.skyhook.model.request;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "key")
public class Key
{
    @Attribute(name = "key")
    private String key = "eJwVwUsKACAIBcB1hxH88LS2il0quns0I0P4g08bx3gVHEWFDZJIUKYLmXrAWoub7wMPMgr_";
    
    @Attribute(name = "username")
    private String username = "interview-candidate";
    
    
    @Override
    public String toString()
    {
        return "Key{" +
                "key='" + key + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
