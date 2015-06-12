package my.sql.resources;

public enum EProperty {

    GENERAL("config.properties"),
    DBase("dbase.properties");


    private final String value;

    private EProperty(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
