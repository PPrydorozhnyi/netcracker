package dao;

import objects.Manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created by drake on 08/02/18.
 *
 * @author P.Pridorozhny
 */
public class ManagerDAO extends DAO {

    public Manager getByID(long id) throws SQLException {
        Manager manager;

        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.NAME nam,o.vers vers, attr.name attr_name, o.PARENT_ID par, attr.ATTR_ID attr_id, " +
                " p.TEXT_VALUE txt, p.NUMBER_VALUE nmbr, p.DATE_VALUE dt\n" +
                "FROM objects o\n" +
                "INNER JOIN attr ON attr.object_type_id = o.OBJECT_TYPE_ID " +
                "LEFT JOIN params p ON p.attr_id = ATTR.attr_id\n" +
                "  AND p.object_id = o.object_id" +
                " WHERE o.OBJECT_TYPE_ID = 1517314340491 AND o.OBJECT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        // TODO: check empty ResultSet
//        if (!rs.next()) {
//            close();
//            return null;
//        }


        manager = new Manager(id);
        //System.out.println(1);
        extractTaskFromResultSet(manager, rs);

        close();

        manager.setProjectID(getProjectID(id));

        return manager;
    }

    private long getProjectID(long id) throws SQLException {

        long projectID = 0;

        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.OBJECT_ID idd FROM OBJECTS o" +
                " WHERE o.OBJECT_TYPE_ID = 1517314348884 AND o.PARENT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        while (rs.next()) {
            projectID = rs.getLong("idd");
        }

        close();

        return projectID;

    }

    private void extractTaskFromResultSet(Manager manager, ResultSet rs) throws SQLException {

        long attr_id;

        while (rs.next()) {
            attr_id = rs.getLong("attr_id");

            manager.setVersion(rs.getLong("vers"));
            manager.setLastName(rs.getString("nam"));
            manager.setDeptID(rs.getLong("par"));

            if (attr_id == 1517315005913L) {
                manager.setSalary(rs.getInt("nmbr"));
            } else if (attr_id == 1517315005901L) {
                manager.setFirstName(rs.getString("txt"));
            }
        }

    }

    public Manager createManager(Manager mgr) {

        Manager manager;
        PreparedStatement myStmt;
        ResultSet rs;
        long id = new Random().nextLong();

        openConnection();

        try {
            rs = myConn.createStatement().executeQuery("SELECT ORA_HASH('objects', 9999) + CURRENT_TIME_MS idd FROM dual");
            if (rs.next()) {
                id = rs.getLong("idd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        manager = new Manager(id);
        manager.copy(mgr);


        try {
            myStmt = myConn.prepareStatement("INSERT INTO objects(object_id, OBJECT_TYPE_ID, name, parent_id, vers) " +
                    "VALUES(?, 1517314340491, ?, ?, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, mgr.getLastName());
            myStmt.setLong(3, mgr.getDeptID());
            myStmt.setLong(4, mgr.getVersion());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, NUMBER_VALUE)\n" +
                    "VALUES(?, 1517315005913, ?)");
            myStmt.setLong(1, id);
            myStmt.setInt(2, mgr.getSalary());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, TEXT_VALUE)\n" +
                    "VALUES(?, 1517315005901, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, mgr.getFirstName());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return manager;
    }

    public void updateManager(Manager manager) {
        PreparedStatement myStmt;
        long id = manager.getId();

        openConnection();


        try {
            myStmt = myConn.prepareStatement("UPDATE objects " +
                    "SET  NAME = ?, parent_id = ?, vers = ?" +
                    " WHERE OBJECT_ID = ?");
            myStmt.setString(1, manager.getLastName());
            myStmt.setLong(2, manager.getDeptID());
            myStmt.setLong(3, manager.getVersion());
            myStmt.setLong(4, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params" +
                    " SET NUMBER_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1517315005913");
            myStmt.setInt(1, manager.getSalary() );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params" +
                    " SET TEXT_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1517315005901");
            myStmt.setString(1, manager.getFirstName() );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();

    }

    public void deleteManager(Manager manager) {
        PreparedStatement myStmt;
        long id = manager.getId();

        openConnection();

        try {
            myStmt = myConn.prepareStatement("DELETE FROM objects " +
                    " WHERE OBJECT_ID = ?");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("DELETE FROM params " +
                    "WHERE OBJECT_ID = ?");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();
    }


}
