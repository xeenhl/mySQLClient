package my.sql.gui.model;

import my.sql.resources.EProperty;
import my.sql.resources.ResourceHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by olehkozlovskyi on 05.05.15.
 */
public class PropertiesManager {

    public static void updateProperty(EProperty prop, String key, String value) {

        try(OutputStream out = new FileOutputStream(ResourceHelper.getPopertiesPath(prop).toString());) {

            Properties properties = new Properties();
            properties.setProperty(key, value);
            properties.store(out, null);

        }catch (java.io.IOException ex) {
            ex.printStackTrace();
        }

    }

    public static Properties getProperties(EProperty prop) {

        try(InputStream input = ResourceHelper.getPopertiesPath(prop).openStream()) {

            Properties properties = new Properties();
            properties.load(input);
            return properties;

        }catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
