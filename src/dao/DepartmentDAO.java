package dao;

import objects.Department;
import objects.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author P.Pridorozhny
 */
public class DepartmentDAO extends DAO {

    //private Connection myConn;

    public DepartmentDAO() {

    }


    public Department getByID(long id) throws SQLException {

        Department department;

        department = new Department(id);
        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.NAME nam,o.vers vers, attr.name attr_name, attr.ATTR_ID attr_id, " +
                " p.TEXT_VALUE txt, p.NUMBER_VALUE nmbr, p.DATE_VALUE dt\n" +
                "FROM objects o\n" +
                "INNER JOIN attr ON attr.object_type_id = o.OBJECT_TYPE_ID " +
                "LEFT JOIN params p ON p.attr_id = ATTR.attr_id\n" +
                "  AND p.object_id = o.object_id" +
                " WHERE o.OBJECT_TYPE_ID = 1511093783249 AND o.OBJECT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        extractDepartmentFromResultSet(department, rs);
        department.setEmployees(getEmployees(id));
        close();

        return department;
    }

    private void extractDepartmentFromResultSet(Department department, ResultSet rs) throws SQLException {

        long attr_id;


        while (rs.next()) {
            attr_id = rs.getLong("attr_id");

            department.setName(rs.getString("nam"));
            department.setVersion(rs.getLong("vers"));

            if (attr_id == 1511096763500L) {
                department.setCompanyName(rs.getString("txt"));
            } else if (attr_id == 1511096765915L) {
                department.setLocation(rs.getString("txt"));
            }
        }

    }

    private ArrayList<Employee> getEmployees(long id) throws SQLException {

        ArrayList<Employee> employees = new ArrayList<>();

        List<Long> employeeIDs = new ArrayList<>();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.OBJECT_ID idd FROM OBJECTS o" +
                " WHERE o.OBJECT_TYPE_ID = 1511093759755 AND o.PARENT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        while (rs.next()) {
            employeeIDs.add(rs.getLong("idd"));
        }

        close();

        for (Long sprintID : employeeIDs) {
            employees.add(employeeDAO.getByID(sprintID));
        }

        System.out.println(employees);

        return employees;
    }


    public Department createDepartment(Department dept) {

        Department department;
        PreparedStatement myStmt;
        ResultSet rs;
        long id = generateID();

        openConnection();

//        try {
//            rs = myConn.createStatement().executeQuery("SELECT ORA_HASH('objects', 99) + CURRENT_TIME_MS idd FROM dual");
//            if (rs.next()) {
//                id = rs.getLong("idd");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        dept.setId(id);
        department = new Department(id);
        department.copy(dept);
        dept = department;

        try {
            myStmt = myConn.prepareStatement("INSERT INTO objects(object_id, OBJECT_TYPE_ID, name, vers) " +
                    "VALUES(?, 1511093783249, ?, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, dept.getName());
            myStmt.setLong(3, dept.getVersion());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, text_value)\n" +
                    "VALUES(?, 1511096763500, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, dept.getCompanyName());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, text_value) " +
                    "VALUES(?, 1511096765915, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, dept.getLocation());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();

        return department;

    }

    /**
     *
     * @param deptS
     * source - from which we copy
     *
     */
    public void updateDepartment(Department deptS) {

        PreparedStatement myStmt;
        long id = deptS.getId();

        openConnection();
        try {
            myStmt = myConn.prepareStatement("UPDATE objects " +
                    "SET  NAME = ?, vers = ?" +
                    " WHERE OBJECT_ID = ?");
            myStmt.setString(1, deptS.getName());
            myStmt.setLong(2, deptS.getVersion());
            myStmt.setLong(3, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params" +
                    " SET text_value = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511096763500");
            myStmt.setString(1, deptS.getCompanyName());
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params" +
                    " SET text_value = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511096765915");
            myStmt.setString(1, deptS.getLocation());
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();


    }

//    public void deleteDepartment(Department dept) {
//
//        PreparedStatement myStmt;
//        long id = dept.getId();
//        //System.out.println(id);
//
//        openConnection();
//
//        try {
//            myStmt = myConn.prepareStatement("DELETE FROM objects" +
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
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511096763500");
//            myStmt.setLong(1, id);
//
//            myStmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            myStmt = myConn.prepareStatement("DELETE FROM params " +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511096765915");
//            myStmt.setLong(1, id);
//
//            myStmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        close();
//
//    }

}
