package Properties;


import java.io.InputStream;
import java.util.Properties;


/**
 * Created by praveen on 12/18/2015.
 */

public class PropertyClass
{
    private final String fileName = "config.properties";
    private Properties properties;

    public PropertyClass()
    {
        properties = new Properties();
        try
        {
            //Load a file that will used in JAR
            InputStream in = getClass().getResourceAsStream(this.fileName);
            properties.load(in);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public String getProp(String key)
    {
        return this.properties.getProperty(key);
    }

    public int getpropInt(String key)
    {
        return Integer.parseInt( this.properties.getProperty(key) );
    }

}
