package my.sql.gui.model;

import com.mysql.jdbc.*;
import my.sql.db.DBHellper;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;

/**
 * Created by olehkozlovskyi on 15.04.15.
 */
public class Test {

    public static void main(String...args) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {


        DBHellper dbHellper = new DBHellper();

        List<String> output = dbHellper.getDBList();

        output.stream().forEach(System.out::println);

        output = dbHellper.getBdTables("fxclient");

        output.stream().forEach(System.out::println);


//        Class.forName("com.mysql.jdbc.Driver").newInstance();
//
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fxclient", "fxclientuser", "fxpass123");
//
//        Statement smt = conn.createStatement();
//
//        ResultSet rs = smt.executeQuery("SHOW tables ");
//        ResultSetMetaData mdata = rs.getMetaData();
//
//        System.out.println("cols = " + mdata.getColumnCount() + " name = " + mdata.getColumnLabel(1));
//        System.out.println(rs.getRow());
//
//
//        while(rs.next()) {
//            String tableName = rs.getString("Tables_in_fxclient");
//            System.out.println("ping " + tableName);
//        }



    }

}
