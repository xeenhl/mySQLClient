package my.sql.resources;


import java.net.URL;

public class ResourceHelper {

    private final static ResourceHelper helper = InstanceHolder.getInstance();

    public static URL getPopertiesPath(EProperty prop) {

        return helper.getClass().getResource(prop.getValue());
    }

    public static URL getFXML(String fileName) {
        return helper.getClass().getResource(fileName);
    }

    private static class InstanceHolder {
        private static final ResourceHelper resourceHelper = new ResourceHelper();
        public  static ResourceHelper getInstance() {
            return resourceHelper;
        }
    }
}
