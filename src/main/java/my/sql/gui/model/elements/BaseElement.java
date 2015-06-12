package my.sql.gui.model.elements;

/**
 * Created by olehkozlovskyi on 07.05.15.
 */
public abstract class BaseElement {

    protected final Object value;
    protected final Type type;

    public BaseElement(Object value, Type type) {
        this.value = value;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public abstract Object getValue();

    @Override
    public abstract String toString();

}
