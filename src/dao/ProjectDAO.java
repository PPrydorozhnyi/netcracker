package dao;

import objects.Project;
import objects.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by drake on 08/02/18.
 *
 * @author P.Pridorozhny
 */
public class ProjectDAO extends DAO {

    public Project getByID(long id) throws SQLException {
        Project project;

        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.NAME nam,o.vers vers, attr.name attr_name, o.PARENT_ID par, attr.ATTR_ID attr_id, " +
                " p.TEXT_VALUE txt, p.NUMBER_VALUE nmbr, p.DATE_VALUE dt\n" +
                "FROM objects o\n" +
                "INNER JOIN attr ON attr.object_type_id = o.OBJECT_TYPE_ID " +
                "LEFT JOIN params p ON p.attr_id = ATTR.attr_id\n" +
                "  AND p.object_id = o.object_id" +
                " WHERE o.OBJECT_TYPE_ID = 1517314348884 AND o.OBJECT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        // TODO: check empty ResultSet
//        if (!rs.next()) {
//            close();
//            return null;
//        }


        project = new Project(id);
        //System.out.println(1);
        extractTaskFromResultSet(project, rs);

        close();

        project.setTasks(getTasks(id));

        return project;
    }

    private void extractTaskFromResultSet(Project project, ResultSet rs) throws SQLException {

        long attr_id;

        while (rs.next()) {
            attr_id = rs.getLong("attr_id");

            project.setVersion(rs.getLong("vers"));
            project.setName(rs.getString("nam"));
            project.setManagerID(rs.getLong("par"));

            if (attr_id == 1517315005924L) {
                project.setStart(rs.getDate("dt"));
            } else if (attr_id == 1517315005928L) {
                project.setEnd(rs.getDate("dt"));
            }
        }

    }

    private List<Task> getTasks(long id) throws SQLException {

        List<Task> tasks = new ArrayList<>();

        List<Long> taskIDs = new ArrayList<>();
        TaskDAO taskDAO = new TaskDAO();

        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.OBJECT_ID idd FROM OBJECTS o" +
                " WHERE o.OBJECT_TYPE_ID = 1517314358141 AND o.PARENT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        while (rs.next()) {
            taskIDs.add(rs.getLong("idd"));
        }

        close();

        for (Long sprintID : taskIDs) {
            tasks.add(taskDAO.getByID(sprintID));
        }

        return tasks;
    }

    public Project createProject(Project prj) {

        Project project;
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

        project = new Project(id);
        project.copy(prj);


        try {
            myStmt = myConn.prepareStatement("INSERT INTO objects(object_id, OBJECT_TYPE_ID, name, parent_id, vers) " +
                    "VALUES(?, 1517314348884, ?, ?, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, prj.getName());
            myStmt.setLong(3, prj.getManagerID());
            myStmt.setLong(4, prj.getVersion());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, DATE_VALUE)\n" +
                    "VALUES(?, 1517315005924, ?)");
            myStmt.setLong(1, id);
            myStmt.setDate(2, new java.sql.Date(prj.getStart().getTimeInMillis()));

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, DATE_VALUE)\n" +
                    "VALUES(?, 1517315005928, ?)");
            myStmt.setLong(1, id);
            myStmt.setDate(2, new java.sql.Date(prj.getEnd().getTimeInMillis()));

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return project;
    }

    public void updateProject(Project project) {
        PreparedStatement myStmt;
        long id = project.getId();

        openConnection();


        try {
            myStmt = myConn.prepareStatement("UPDATE objects " +
                    "SET  NAME = ?, parent_id = ?, vers = ?" +
                    " WHERE OBJECT_ID = ?");
            myStmt.setString(1, project.getName());
            myStmt.setLong(2, project.getManagerID());
            myStmt.setLong(3, project.getVersion());
            myStmt.setLong(4, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params" +
                    " SET DATE_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1517315005924");
            myStmt.setDate(1, new java.sql.Date(project.getStart().getTimeInMillis()) );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params" +
                    " SET DATE_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1517315005928");
            myStmt.setDate(1, new java.sql.Date(project.getEnd().getTimeInMillis()) );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();

    }

    public void deleteProject(Project project) {
        PreparedStatement myStmt;
        long id = project.getId();

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
