package my.sql.gui.model.elements;

/**
 * Created by olehkozlovskyi on 13.05.15.
 */
public class VarCharElement extends BaseElement {

    public VarCharElement(String string) {
        super(string, Type.VARCHAR);
    }

    @Override
    public String getValue() {
        return (String)value;
    }

    @Override
    public String toString() {
        return (String)value;
    }
}
