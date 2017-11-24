package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author P.Pridorozhny
 */
public abstract class DAO {
    protected Connection myConn;



    public void openConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            myConn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "drakeNC", "drake");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            myConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
