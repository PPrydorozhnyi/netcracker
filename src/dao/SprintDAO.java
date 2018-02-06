package dao;

import objects.Sprint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created by drake on 06/02/18.
 *
 * @author P.Pridorozhny
 */
public class SprintDAO extends DAO {

    public Sprint getByID(long id) throws SQLException {
        Sprint sprint;

        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.NAME nam,o.vers vers, attr.name attr_name, o.PARENT_ID par, attr.ATTR_ID attr_id, " +
                " p.TEXT_VALUE txt, p.NUMBER_VALUE nmbr, p.DATE_VALUE dt\n" +
                "FROM objects o\n" +
                "INNER JOIN attr ON attr.object_type_id = o.OBJECT_TYPE_ID " +
                "LEFT JOIN params p ON p.attr_id = ATTR.attr_id\n" +
                "  AND p.object_id = o.object_id" +
                " WHERE o.OBJECT_TYPE_ID = 1517314355062 AND o.OBJECT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        if (!rs.next()) {
            close();
            return null;
        }

        sprint = new Sprint(id);
        extractSprintFromResultSet(sprint, rs);

        close();

        return sprint;
    }

    private void extractSprintFromResultSet(Sprint sprint, ResultSet rs) throws SQLException {

        System.out.println(rs.next());

            long attr_id;

            while (rs.next()) {
                attr_id = rs.getLong("attr_id");

                sprint.setVersion(rs.getLong("vers"));
                sprint.setName(rs.getString("nam"));
                sprint.setTaskID(rs.getLong("par"));

                if (attr_id == 1517315005932L) {
                    sprint.setDifficulty(rs.getInt("nmbr"));
                }
            }

    }

    public Sprint createSprint(Sprint spr) {

        Sprint sprint;
        PreparedStatement myStmt;
        ResultSet rs;
        long id = new Random().nextLong();

        openConnection();

        try {
            rs = myConn.createStatement().executeQuery("SELECT ORA_HASH('objects', 9999) + CURRENT_TIME_MS iddd FROM dual");
            if (rs.next()) {
                id = rs.getLong("iddd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sprint = new Sprint(id);
        sprint.copy(spr);


        try {
            myStmt = myConn.prepareStatement("INSERT INTO objects(object_id, OBJECT_TYPE_ID, name, parent_id, vers) " +
                    "VALUES(?, 1517314355062, ?, ?, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, spr.getName());
            myStmt.setLong(3, spr.getTaskID());
            myStmt.setLong(4, spr.getVersion());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, NUMBER_VALUE)\n" +
                    "VALUES(?, 1517315005932, ?)");
            myStmt.setLong(1, id);
            myStmt.setLong(2, spr.getDifficulty());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sprint;
    }

    public void updateSprint(Sprint sprint) {
        PreparedStatement myStmt;
        long id = sprint.getId();

        openConnection();


        try {
            myStmt = myConn.prepareStatement("UPDATE objects " +
                    "SET  NAME = ?, parent_id = ?, vers = ?" +
                    " WHERE OBJECT_ID = ?");
            myStmt.setString(1, sprint.getName());
            myStmt.setLong(2, sprint.getTaskID());
            myStmt.setLong(3, sprint.getVersion());
            myStmt.setLong(4, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params" +
                    " SET NUMBER_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1517315005932");
            myStmt.setInt(1, sprint.getDifficulty() );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();

    }

    public void deleteSprint(Sprint sprint) {
        PreparedStatement myStmt;
        long id = sprint.getId();

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
