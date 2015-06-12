package my.sql.data.model.elements;

import my.sql.gui.model.elements.IntElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IntElementTest {

    @Test
    public void testGetValue() throws Exception {
        IntElement elm = new IntElement(125);

        Integer value = elm.getValue();

        Assert.assertEquals(value, new Integer(125));


    }
}