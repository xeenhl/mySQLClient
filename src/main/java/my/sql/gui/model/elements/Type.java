package my.sql.gui.model.elements;

import java.sql.Types;

/**
 * Created by olehkozlovskyi on 13.05.15.
 */
public enum Type {

    INT(Types.INTEGER),
    CHAR(Types.CHAR),
    BOOLEAN(Types.BOOLEAN),
    DATE(Types.DATE),
    FLOAT(Types.FLOAT),
    DOUBLE(Types.DOUBLE),
    BINARY(Types.BINARY),
    VARCHAR(Types.VARCHAR),
    OTHER(-678);



    private final int SqlType;

    private Type(int SqlType) {
        this.SqlType = SqlType;
    }

    public int getSqlType() {
        return SqlType;
    }
}
