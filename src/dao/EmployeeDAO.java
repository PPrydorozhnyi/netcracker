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
public class EmployeeDAO extends DAO {

    public EmployeeDAO() {

        openConnection();
    }


    public Employee getByID(long id) throws SQLException {

        Employee employee = new Employee();
        ResultSet rs;
        PreparedStatement myStmt;

        myStmt = myConn.prepareStatement("select objects.NAME nam, fi_name.TEXT_VALUE fname, " +
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
                "WHERE object_types.OBJECT_TYPE_ID = 1511093759755 and objects.OBJECT_ID = ?");
        myStmt.setLong(1, id);
        rs = myStmt.executeQuery();

        if (rs.next()) {
            employee.setLastName(rs.getString("nam"));
            employee.setFirstName(rs.getString("fname"));
            employee.setJob(rs.getString("jobb"));
            employee.setHiredate(rs.getDate("hiredatee"));
            employee.setSalary(rs.getInt("sall"));
            employee.setCommission(rs.getInt("commm"));
            employee.setDeptNumber(rs.getLong("deptnoo"));
        }
        employee.setId(id);

        employee.setDepartment(getDept(employee.getDeptNumber()));

        return employee;
    }

    private Department getDept(long deptNumber) throws SQLException {

        Department department = new Department();
        ResultSet rs;
        PreparedStatement myStmt;

        myStmt = myConn.prepareStatement("select objects.NAME nam, comp_name.TEXT_VALUE cName, " +
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
        myStmt.setLong(1, deptNumber);
        rs = myStmt.executeQuery();

        while (rs.next()) {
            department.setId(deptNumber);
            department.setName(rs.getString("nam"));
            department.setCompanyName(rs.getString("cName"));
            department.setLocation(rs.getString("locc"));
        }

        return department;
    }

    public ArrayList<Employee> getByLastName(String lastName) throws SQLException {

        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee;
        ResultSet rs;
        PreparedStatement myStmt ;

        myStmt = myConn.prepareStatement("select objects.OBJECT_ID idd, objects.NAME nam, fi_name.TEXT_VALUE fname, " +
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
//                "where \"Object_types\".\"name\"='people' and aid.\"number_value\"=11 and \n" +
//                "apt.\"number_value\"=3;");

                "WHERE object_types.OBJECT_TYPE_ID = 1511093759755 and objects.NAME = ?");
        myStmt.setString(1, lastName);
        rs = myStmt.executeQuery();


        while (rs.next()) {
            employee = new Employee();
            employee.setId(rs.getLong("idd"));
            employee.setLastName(rs.getString("nam"));
            employee.setFirstName(rs.getString("fname"));
            employee.setJob(rs.getString("jobb"));
            employee.setHiredate(rs.getDate("hiredatee"));
            employee.setSalary(rs.getInt("sall"));
            employee.setCommission(rs.getInt("commm"));
            employee.setDeptNumber(rs.getLong("deptnoo"));
            employee.setDepartment(getDept(employee.getDeptNumber()));
            employees.add(employee);
        }

        return employees;
    }

    public void createEmployee(Employee emp) {

        PreparedStatement myStmt;
        ResultSet rs;
        long id = new Random().nextLong();

        try {
            rs = myConn.createStatement().executeQuery("SELECT ORA_HASH('objects', 9999) + CURRENT_TIME_MS idd FROM dual");
            if (rs.next()) {
                id = rs.getLong("idd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        emp.setId(id);

        try {
            myStmt = myConn.prepareStatement("INSERT INTO objects(object_id, OBJECT_TYPE_ID, name) " +
                    "VALUES(?, 1511093759755, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, emp.getLastName());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, text_value)\n" +
                    "VALUES(?, 1511095081213, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, emp.getFirstName());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, text_value) " +
                    "VALUES(?, 1511095084373, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, emp.getJob());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, date_value) " +
                    "VALUES(?, 1511095087262, ?)");
            myStmt.setLong(1, id);
            myStmt.setDate(2, new java.sql.Date(emp.getHiredate().getTimeInMillis()) );

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, number_value)\n" +
                    "VALUES(?, 1511095090681, ?)");
            myStmt.setLong(1, id);
            myStmt.setInt(2, emp.getSalary() );

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, number_value)\n" +
                    "VALUES(?, 1511095093511, ?)");
            myStmt.setLong(1, id);
            myStmt.setInt(2, emp.getCommission() );

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, number_value)\n" +
                    "VALUES(?, 1511095096402, ?)");
            myStmt.setLong(1, id);
            myStmt.setLong(2, emp.getDeptNumber() );

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateEmployee(Employee empS, Employee empD) {

        PreparedStatement myStmt;
        long id = empD.getId();

        try {
            myStmt = myConn.prepareStatement("UPDATE objects " +
                    "SET  NAME = ?" +
                    " WHERE OBJECT_ID = ?");
            myStmt.setString(1, empS.getLastName());
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params/*(object_id, attr_id, text_value)\n*/" +
                    " SET /*(?, 1511095081213, ?)*/ text_value = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095081213");
            myStmt.setString(1, empS.getFirstName());
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params/*(object_id, attr_id, text_value)\n*/" +
                    " SET /*(?, 1511095081213, ?)*/ text_value = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095084373");
            myStmt.setString(1, empS.getJob());
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params/*(object_id, attr_id, text_value)\n*/" +
                    " SET /*(?, 1511095081213, ?)*/ date_value = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095087262");
            myStmt.setDate(1, new java.sql.Date(empS.getHiredate().getTimeInMillis()) );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params/*(object_id, attr_id, text_value)\n*/" +
                    " SET /*(?, 1511095081213, ?)*/ NUMBER_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095090681");
            myStmt.setInt(1, empS.getSalary() );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params/*(object_id, attr_id, text_value)\n*/" +
                    " SET /*(?, 1511095081213, ?)*/ NUMBER_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095093511");
            myStmt.setInt(1, empS.getCommission() );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params/*(object_id, attr_id, text_value)\n*/" +
                    " SET /*(?, 1511095081213, ?)*/ NUMBER_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095096402");
            myStmt.setLong(1, empS.getDeptNumber() );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteEmployee(Employee emp) {

        PreparedStatement myStmt;
        long id = emp.getId();
        System.out.println(id);

        try {
            myStmt = myConn.prepareStatement("DELETE FROM objects " +
                    " WHERE OBJECT_ID = ?");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("DELETE FROM params/*(object_id, attr_id, text_value)\n*/" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095081213");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("DELETE FROM params/*(object_id, attr_id, text_value)\n*/" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095084373");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("DELETE FROM params/*(object_id, attr_id, text_value)\n*/" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095087262");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("DELETE FROM params/*(object_id, attr_id, text_value)\n*/" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095090681");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("DELETE FROM params/*(object_id, attr_id, text_value)\n*/" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095093511");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("DELETE FROM params /*(object_id, attr_id, text_value)\n*/" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095096402");
            myStmt.setLong(1, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


//    public void close() throws SQLException {
//        myConn.close();
//    }

}
