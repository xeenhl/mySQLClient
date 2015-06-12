package my.sql.gui.model.elements;

import javafx.beans.property.SimpleStringProperty;

import java.util.*;

/**
 * Created by olehkozlovskyi on 13.05.15.
 */
public class Row {

    private Map<String, SimpleStringProperty> elements = new HashMap<>();

    public Map<String, SimpleStringProperty> getElements() {
        return elements;
    }

    public void addColumn(String columnLable, String element) {

        elements.put(columnLable, new SimpleStringProperty(element));
    }

    public void updateElement(String columnLable, String newElement) {
        elements.replace(columnLable, new SimpleStringProperty(newElement));
    }

}
