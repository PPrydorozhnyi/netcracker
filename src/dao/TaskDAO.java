package dao;

import objects.Sprint;
import objects.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drake on 06/02/18.
 *
 * @author P.Pridorozhny
 */
public class TaskDAO extends DAO {

    public Task getByID(long id) throws SQLException {
        Task task;

        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.NAME nam,o.vers vers, attr.name attr_name, o.PARENT_ID par, attr.ATTR_ID attr_id, " +
                " p.TEXT_VALUE txt, p.NUMBER_VALUE nmbr, p.DATE_VALUE dt\n" +
                "FROM objects o\n" +
                "INNER JOIN attr ON attr.object_type_id = o.OBJECT_TYPE_ID " +
                "LEFT JOIN params p ON p.attr_id = ATTR.attr_id\n" +
                "  AND p.object_id = o.object_id" +
                " WHERE o.OBJECT_TYPE_ID = 1517314358141 AND o.OBJECT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        // TODO: check empty ResultSet
//        if (!rs.next()) {
//            close();
//            return null;
//        }


        task = new Task(id);
        System.out.println(1);
        extractTaskFromResultSet(task, rs);

        close();

        task.setSprints(getSprints(id));

        return task;
    }

    private void extractTaskFromResultSet(Task task, ResultSet rs) throws SQLException {

        long attr_id;

        while (rs.next()) {
            attr_id = rs.getLong("attr_id");

            task.setVersion(rs.getLong("vers"));
            task.setName(rs.getString("nam"));
            task.setProjectID(rs.getLong("par"));

            if (attr_id == 1517315005941L) {
                task.setTaskTime(rs.getInt("nmbr"));
            }
        }

    }

    private List<Sprint> getSprints(long id) throws SQLException {

        List<Sprint> sprints = new ArrayList<>();

        List<Long> sprintIDs = new ArrayList<>();
        SprintDAO sprintDAO = new SprintDAO();

        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.OBJECT_ID idd FROM OBJECTS o" +
                                " WHERE o.OBJECT_TYPE_ID = 1517314355062 AND o.PARENT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        while (rs.next()) {
            sprintIDs.add(rs.getLong("idd"));
        }

        close();

        for (Long sprintID : sprintIDs) {
            sprints.add(sprintDAO.getByID(sprintID));
        }

        return sprints;
    }

    public Task createTask(Task tsk) {

        Task task;
        PreparedStatement myStmt;
        ResultSet rs;
        long id = generateID();

        openConnection();

//        try {
//            rs = myConn.createStatement().executeQuery("SELECT ORA_HASH('objects', 9999) + CURRENT_TIME_MS idd FROM dual");
//            if (rs.next()) {
//                id = rs.getLong("idd");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        task = new Task(id);
        task.copy(tsk);


        try {
            myStmt = myConn.prepareStatement("INSERT INTO objects(object_id, OBJECT_TYPE_ID, name, parent_id, vers) " +
                    "VALUES(?, 1517314358141, ?, ?, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, tsk.getName());
            myStmt.setLong(3, tsk.getProjectID());
            myStmt.setLong(4, tsk.getVersion());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, NUMBER_VALUE)\n" +
                    "VALUES(?, 1517315005941, ?)");
            myStmt.setLong(1, id);
            myStmt.setLong(2, task.getTaskTime());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();

        return task;
    }

    public void updateTask(Task task) {
        PreparedStatement myStmt;
        long id = task.getId();

        openConnection();


        try {
            myStmt = myConn.prepareStatement("UPDATE objects " +
                    "SET  NAME = ?, parent_id = ?, vers = ?" +
                    " WHERE OBJECT_ID = ?");
            myStmt.setString(1, task.getName());
            myStmt.setLong(2, task.getProjectID());
            myStmt.setLong(3, task.getVersion());
            myStmt.setLong(4, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params" +
                    " SET NUMBER_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1517315005941");
            myStmt.setInt(1, task.getTaskTime() );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();

    }

//    public void deleteTask(Task task) {
//        PreparedStatement myStmt;
//        long id = task.getId();
//
//        openConnection();
//
//        try {
//            myStmt = myConn.prepareStatement("DELETE FROM objects " +
//                    " WHERE OBJECT_ID = ?");
//            myStmt.setLong(1, id);
//
//            myStmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            myStmt = myConn.prepareStatement("DELETE FROM params " +
//                    "WHERE OBJECT_ID = ?");
//            myStmt.setLong(1, id);
//
//            myStmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        close();
//    }
}
