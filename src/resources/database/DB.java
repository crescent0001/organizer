package resources.database;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hhf on 6/28/17.
 */
public class DB {
    private final String DRIVER = "com.mysql.jdbc.Driver";
    //private final String DATABASE_URL="jdbc:mysql://localhost:3306/organizer?useSSL=false";
    private static final String DATABASE_URL = "jdbc:mysql://172.27.190.104:3306/organizer?useSSL=false";

    private static String userName = "user";
    private static String password = "Password&&123H";


    public DB() {
        /*try{
            Class.forName(DRIVER);
        }catch(ClassNotFoundException e){System.out.println("Driver not fount :"+e.getMessage());}*/
    }

    private static Connection openConnection() {
        try {

            return DriverManager.getConnection(DATABASE_URL, userName, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public static void update(String query) {
        Connection connection = openConnection();
        Statement statement = null;
        try {
            if (connection != null) {
                statement = connection.createStatement();
                statement.execute(query);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static Connection updatePrepare(String query) throws SQLException {
        return openConnection();
    }

    public static CachedRowSet read(String query) {
        Connection connection = openConnection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            if (connection != null) {
                statement = connection.createStatement();
                rs = statement.executeQuery(query);

                // Test
                CachedRowSet cachedRowSet = new CachedRowSetImpl();
                cachedRowSet.populate(rs);
                return cachedRowSet;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;

    }
}