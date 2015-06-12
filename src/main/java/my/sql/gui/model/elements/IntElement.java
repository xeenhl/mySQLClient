package my.sql.gui.model.elements;

import java.sql.Types;

/**
 * Created by olehkozlovskyi on 07.05.15.
 */
public class IntElement extends BaseElement {

    public IntElement(Integer value) {
        super(value, Type.INT);
    }

    @Override
    public Integer getValue() {
        return (Integer)value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
