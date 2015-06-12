package my.sql.gui.controllers.model;

/**
 * Created by olehkozlovskyi on 07.05.15.
 */
public class Element {

    private final Type type;
    private final String value;

    public Element(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }

    public enum Type {
        DB, TABLE, OTHER
    }


}
