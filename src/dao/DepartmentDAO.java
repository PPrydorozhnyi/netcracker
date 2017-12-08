package dao;

import objects.Department;
import objects.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author P.Pridorozhny
 */
public class DepartmentDAO extends DAO {

    //private Connection myConn;

    public DepartmentDAO() {

        openConnection();
    }


    public Department getByID(long id) throws SQLException {

        Department department = new Department(id);
        ResultSet rs;
        PreparedStatement myStmt;

        myStmt = myConn.prepareStatement("select objects.NAME nam, objects.vers vers, comp_name.TEXT_VALUE cName, " +
                "loc.TEXT_VALUE locc " +
                "from objects join object_types on " +
                "object_types.object_type_id = objects.object_type_id " +
                "join attr on attr.object_type_id = object_types.object_type_id " +
                "and attr.name='COMPANY' " +
                "join params comp_name on comp_name.attr_id = attr.attr_id and " +
                "comp_name.object_id = objects.object_id " +
                "join attr loc_attr on " +
                "loc_attr.object_type_id = Object_types.object_type_id and " +
                "loc_attr.name='LOCATION'" +
                "join Params loc on loc.attr_id=loc_attr.attr_id" +
                " and loc.object_id=Objects.object_id" +
                " WHERE object_types.OBJECT_TYPE_ID = 1511093783249 and objects.OBJECT_ID = ?");
        myStmt.setLong(1, id);
        rs = myStmt.executeQuery();

        while (rs.next()) {
            department.setName(rs.getString("nam"));
            department.setVersion(rs.getLong("vers"));
            department.setCompanyName(rs.getString("cName"));
            department.setLocation(rs.getString("locc"));
        }
//        department.setId(id);
        getEmployees(id, department);


        return department;
    }

    private void getEmployees(long id, Department department) throws SQLException {

        Employee employee;
        ResultSet rs;
        PreparedStatement myStmt;

        myStmt = myConn.prepareStatement("select objects.OBJECT_ID idd,  objects.vers vers, objects.NAME nam, fi_name.TEXT_VALUE fname, " +
                "job.TEXT_VALUE jobb, hiredate.DATE_VALUE hiredatee, sal.NUMBER_VALUE sall, " +
                "comm.NUMBER_VALUE commm, deptno.NUMBER_VALUE deptnoo " +
                "from objects join object_types on " +
                "object_types.object_type_id = objects.object_type_id " +
                "join attr on attr.object_type_id = object_types.object_type_id " +
                "and attr.name='ENAME' " +
                "join params fi_name on fi_name.attr_id = attr.attr_id and " +
                "fi_name.object_id = objects.object_id " +
                "join attr job_attr on " +
                "job_attr.object_type_id = Object_types.object_type_id and " +
                "job_attr.name='JOB'\n" +
                "join Params job on job.attr_id=job_attr.attr_id" +
                " and job.object_id=Objects.object_id" +
                " join attr hiredate_attr on " +
                "hiredate_attr.object_type_id=object_types.object_type_id and " +
                "hiredate_attr.name='HIREDATE'" +
                "join params hiredate on hiredate.attr_id=hiredate_attr.attr_id and " +
                "hiredate.object_id=objects.object_id" +
                " join attr sal_attr on " +
                "sal_attr.object_type_id=object_types.object_type_id and " +
                "sal_attr.name='SAL'" +
                "join Params sal on sal.attr_id=sal_attr.attr_id and " +
                "sal.object_id=objects.object_id" +
                " join attr comm_attr on " +
                "comm_attr.object_type_id=Object_types.object_type_id and " +
                "comm_attr.name='COMM'" +
                "join Params comm on comm.attr_id=comm_attr.attr_id and " +
                "comm.object_id=Objects.object_id" +
                " join attr deptno_attr on " +
                "deptno_attr.object_type_id=object_types.object_type_id and " +
                "deptno_attr.name='DEPTNO'" +
                "join Params deptno on deptno.attr_id=deptno_attr.attr_id and " +
                "deptno.object_id=objects.object_id " +
                "WHERE object_types.OBJECT_TYPE_ID = 1511093759755 and deptno.NUMBER_VALUE = ?");
        myStmt.setLong(1, id);
        rs = myStmt.executeQuery();

        while (rs.next()) {
            employee = new Employee(rs.getLong("idd"));

            employee.setLastName(rs.getString("nam"));
            employee.setVersion(rs.getLong("vers"));
            employee.setFirstName(rs.getString("fname"));
            employee.setJob(rs.getString("jobb"));
            employee.setHiredate(rs.getDate("hiredatee"));
            employee.setSalary(rs.getInt("sall"));
            employee.setCommission(rs.getInt("commm"));
            employee.setDeptNumber(rs.getLong("deptnoo"));
            employee.setDepartment(department);

            department.getEmployees().add(employee);
        }
    }

    public ArrayList<Department> getByDeptName(String deptName) throws SQLException {

        ArrayList<Department> departments = new ArrayList<>();
        Department department;
        ResultSet rs;
        PreparedStatement myStmt ;

        myStmt = myConn.prepareStatement("select objects.OBJECT_ID idd, objects.vers vers, objects.NAME nam, comp_name.TEXT_VALUE cName, " +
                "loc.TEXT_VALUE locc " +
                "from objects join object_types on " +
                "object_types.object_type_id = objects.object_type_id " +
                "join attr on attr.object_type_id = object_types.object_type_id " +
                "and attr.name='COMPANY' " +
                "join params comp_name on comp_name.attr_id = attr.attr_id and " +
                "comp_name.object_id = objects.object_id " +
                "join attr loc_attr on " +
                "loc_attr.object_type_id = Object_types.object_type_id and " +
                "loc_attr.name='LOCATION'" +
                "join Params loc on loc.attr_id=loc_attr.attr_id" +
                " and loc.object_id=Objects.object_id " +
                "WHERE object_types.OBJECT_TYPE_ID = 1511093783249 and objects.NAME = ?");
        myStmt.setString(1, deptName);
        rs = myStmt.executeQuery();

        while (rs.next()) {
            department = new Department(rs.getLong("idd"));
//            department.setId();
            department.setName(rs.getString("nam"));
            department.setVersion(rs.getLong("vers"));
            department.setCompanyName(rs.getString("cName"));
            department.setLocation(rs.getString("locc"));
            getEmployees(department.getId(), department);
            departments.add(department);
        }

        return departments;
    }

    public void createDepartment(Department dept) {

        Department department;
        PreparedStatement myStmt;
        ResultSet rs;
        long id = new Random().nextLong();

        try {
            rs = myConn.createStatement().executeQuery("SELECT ORA_HASH('objects', 99) + CURRENT_TIME_MS idd FROM dual");
            if (rs.next()) {
                id = rs.getLong("idd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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


    }

    /**
     *
     * @param deptS
     * source - from which we copy
     * @param deptD
     * destination - final object which we update
     */
    public void updateDepartment(Department deptS, Department deptD) {

        PreparedStatement myStmt;
        long id = deptD.getId();

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


    }

    public void deleteDepartment(Department dept) {

        PreparedStatement myStmt;
        long id = dept.getId();
        //System.out.println(id);

        try {
            myStmt = myConn.prepareStatement("DELETE FROM objects" +
                    " WHERE OBJECT_ID = ?");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("DELETE FROM params " +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511096763500");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("DELETE FROM params " +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511096765915");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
