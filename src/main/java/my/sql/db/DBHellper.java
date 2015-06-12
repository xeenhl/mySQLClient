package my.sql.db;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import my.sql.gui.model.PropertiesManager;
import my.sql.gui.model.elements.*;
import my.sql.resources.EProperty;

import java.awt.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import java.util.List;

public class DBHellper {

    private final String driver;
    private final String user;
    private final String password;
    private final String dbURL;




    public DBHellper() {
        Properties prop = PropertiesManager.getProperties(EProperty.DBase);
        driver = prop.getProperty("jdbcDriver");
        user = prop.getProperty("jdbcUser");
        password = prop.getProperty("jdbcPassword");
        dbURL = prop.getProperty("jdbcURL");

        try {
            getClass().forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getDBList() {

        final String QUERY = "SHOW DATABASES";
        final List<String> DBASES = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbURL, user, password)){

            Statement smt = conn.createStatement();
            ResultSet res = smt.executeQuery(QUERY);

            while(res.next()) {
                DBASES.add(res.getString(1));
            }

            return DBASES;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> getBdTables(String dbname) {

        final String QUERY = "SHOW TABLES";
        final List<String> TABLES = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbURL, user, password)){

            Statement smt = conn.createStatement();
            smt.execute("USE "+dbname);
            ResultSet res = smt.executeQuery(QUERY);

            while(res.next()) {
                TABLES.add(res.getString(1));
            }

            return TABLES;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<String, List<BaseElement>> getTop100FromTable(String dataBase, String table) {

        try (Connection conn = DriverManager.getConnection(dbURL, user, password)){

            Map<String, List<BaseElement>> resultMap = new HashMap<>();

            Statement smt = conn.createStatement();
            smt.execute("USE "+dataBase);

            String query = "SELECT * FROM "+table+" LIMIT 100";
            ResultSet result = smt.executeQuery(query);

            int columns = result.getMetaData().getColumnCount();

            Map<String, Integer> LableType = new HashMap<>();

            for(int i = 1; i<=columns; i++) {

                String lable = result.getMetaData().getColumnLabel(i);
                int type = result.getMetaData().getColumnType(i);
                LableType.put(lable, type);
                resultMap.put(lable, new ArrayList<>());

            }

            while(result.next()) {
                resultMap.entrySet().stream().forEach( v -> {
                    try {
                        v.getValue().add(getEtelemt(LableType.get(v.getKey()), result, v.getKey()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }

            return resultMap;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean update(String table, String id, String column, BaseElement value) {


        try(Connection conn = DriverManager.getConnection(dbURL, user, password)){

            StringBuilder builder = new StringBuilder();

            PreparedStatement update = conn.prepareStatement(

            builder.append("UPDATE ")
                    .append(table)
                    .append(" SET ")
                    .append(column)
                    .append(" = ")
                    .append(" ? ")
                    .append("WHERE id = ")
                    .append(id)
                    .toString()
            );

            setPropperTypeElement(update, value);
            update.execute();
            return true;

        } catch (SQLException e) {
            return false;
//            e.printStackTrace();
        }
    }

    private BaseElement getEtelemt(int type, ResultSet rSet, String Lable) throws SQLException {

        switch (type) {
            case Types.INTEGER:
                return new IntElement(rSet.getInt(Lable));
            case Types.VARCHAR:
                return new VarCharElement(rSet.getString(Lable));
            default:
                return new OtherElement();
        }

    }

    private void setPropperTypeElement(PreparedStatement smt, BaseElement element) throws SQLException {

        switch (element.getType().getSqlType()) {
            case Types.INTEGER:
                smt.setInt(1, ((IntElement)element).getValue());
                break;
            case Types.VARCHAR:
                smt.setString(1, ((VarCharElement) element).getValue());
                break;
            default:
                System.out.println("Nothing to update");
                break;
        }

    }






}
