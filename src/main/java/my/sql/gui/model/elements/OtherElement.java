package my.sql.gui.model.elements;

/**
 * Created by olehkozlovskyi on 13.05.15.
 */
public class OtherElement extends BaseElement{

    public OtherElement() {
        super(new Object(), Type.OTHER);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Unknown";
    }
}
