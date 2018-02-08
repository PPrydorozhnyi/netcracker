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

    }


    public Employee getByID(long id) throws SQLException {

        Employee employee;

        employee = new Employee(id);
        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

//        myStmt = myConn.prepareStatement("select objects.NAME nam, objects.vers vers, fi_name.TEXT_VALUE fname, " +
//                "job.TEXT_VALUE jobb, hiredate.DATE_VALUE hiredatee, sal.NUMBER_VALUE sall, " +
//                "comm.NUMBER_VALUE commm, deptno.NUMBER_VALUE deptnoo " +
//                "from objects join object_types on " +
//                "object_types.object_type_id = objects.object_type_id " +
//                "join attr on attr.object_type_id = object_types.object_type_id " +
//                "and attr.name='ENAME' " +
//                "join params fi_name on fi_name.attr_id = attr.attr_id and " +
//                "fi_name.object_id = objects.object_id " +
//                "join attr job_attr on " +
//                "job_attr.object_type_id = Object_types.object_type_id and " +
//                "job_attr.name='JOB'\n" +
//                "join Params job on job.attr_id=job_attr.attr_id" +
//                " and job.object_id=Objects.object_id" +
//                " join attr hiredate_attr on " +
//                "hiredate_attr.object_type_id=object_types.object_type_id and " +
//                "hiredate_attr.name='HIREDATE'" +
//                "join params hiredate on hiredate.attr_id=hiredate_attr.attr_id and " +
//                "hiredate.object_id=objects.object_id" +
//                " join attr sal_attr on " +
//                "sal_attr.object_type_id=object_types.object_type_id and " +
//                "sal_attr.name='SAL'" +
//                "join Params sal on sal.attr_id=sal_attr.attr_id and " +
//                "sal.object_id=objects.object_id" +
//                " join attr comm_attr on " +
//                "comm_attr.object_type_id=Object_types.object_type_id and " +
//                "comm_attr.name='COMM'" +
//                "join Params comm on comm.attr_id=comm_attr.attr_id and " +
//                "comm.object_id=Objects.object_id" +
//                " join attr deptno_attr on " +
//                "deptno_attr.object_type_id=object_types.object_type_id and " +
//                "deptno_attr.name='DEPTNO'" +
//                "join Params deptno on deptno.attr_id=deptno_attr.attr_id and " +
//                "deptno.object_id=objects.object_id " +
//                "WHERE object_types.OBJECT_TYPE_ID = 1511093759755 and objects.OBJECT_ID = ?");
//        myStmt.setLong(1, id);

        myStmt = myConn.prepareStatement("SELECT o.NAME nam,o.vers vers, attr.name attr_name, attr.ATTR_ID attr_id, " +
                " p.TEXT_VALUE txt, p.NUMBER_VALUE nmbr, p.DATE_VALUE dt\n" +
                "FROM objects o\n" +
                "INNER JOIN attr ON attr.object_type_id = o.OBJECT_TYPE_ID " +
                "LEFT JOIN params p ON p.attr_id = ATTR.attr_id\n" +
                "  AND p.object_id = o.object_id" +
                " WHERE o.OBJECT_TYPE_ID = 1511093759755 AND o.OBJECT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

//        if (rs.next()) {
//            employee.setLastName(rs.getString("nam"));
//            employee.setVersion(rs.getLong("vers"));
//            employee.setFirstName(rs.getString("fname"));
//            employee.setJob(rs.getString("jobb"));
//            employee.setHiredate(rs.getDate("hiredatee"));
//            employee.setSalary(rs.getInt("sall"));
//            employee.setCommission(rs.getInt("commm"));
//            employee.setDeptNumber(rs.getLong("deptnoo"));
//        }

        extractEmployeeFromResultSet(employee, rs);

        employee.setDepartment(getDept(employee.getDeptNumber()));
        close();

        return employee;
    }

    private void extractEmployeeFromResultSet(Employee employee, ResultSet rs) throws SQLException {

        long attr_id;

// TODO: extract manager and task
        while (rs.next()) {
            attr_id = rs.getLong("attr_id");

            employee.setLastName(rs.getString("nam"));
            employee.setVersion(rs.getLong("vers"));

            if (attr_id == 1511095081213L) {
                employee.setFirstName(rs.getString("txt"));
            } else if (attr_id == 1511095084373L) {
                employee.setJob(rs.getString("txt"));
            } else if (attr_id == 1511095087262L) {
                employee.setHiredate(rs.getDate("dt"));
            } else if (attr_id == 1511095090681L) {
                employee.setSalary(rs.getInt("nmbr"));
            } else if (attr_id == 1511095093511L) {
                employee.setCommission(rs.getInt("nmbr"));
            } else if (attr_id == 1511095096402L) {
                employee.setDeptNumber(rs.getLong("nmbr"));
            }
        }

    }

    private Department getDept(long deptNumber) throws SQLException {

        Department department;

        DepartmentDAO  departmentDAO = new DepartmentDAO();

        departmentDAO.openConnection();

         department = departmentDAO.getByID(deptNumber);

        departmentDAO.close();

        return department;
    }

    @Deprecated
    public ArrayList<Employee> getByLastName(String lastName) throws SQLException {

        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee;
        ResultSet rs;
        PreparedStatement myStmt ;

        openConnection();

        myStmt = myConn.prepareStatement("select objects.OBJECT_ID idd, objects.vers vers, objects.NAME nam, fi_name.TEXT_VALUE fname, " +
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
            employee = new Employee(rs.getLong("idd"));
//            employee.setId(rs.getLong("idd"));
            employee.setVersion(rs.getLong("vers"));
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

        close();

        return employees;
    }

    public Employee createEmployee(Employee emp) {

        Employee employee;
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

        employee = new Employee(id);
        employee.copy(emp);
        emp = employee;

        try {
            myStmt = myConn.prepareStatement("INSERT INTO objects(object_id, OBJECT_TYPE_ID, name, parent_id, vers) " +
                    "VALUES(?, 1511093759755, ?, ?, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, emp.getLastName());
            myStmt.setLong(3, emp.getDeptNumber());
            myStmt.setLong(4, emp.getVersion());

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

        close();

        return employee;
    }

    public void updateEmployee(Employee empS) {

        PreparedStatement myStmt;
        long id = empS.getId();

        openConnection();

        try {
            myStmt = myConn.prepareStatement("UPDATE objects " +
                    "SET  NAME = ?, parent_id = ?, vers = ?" +
                    " WHERE OBJECT_ID = ?");
            myStmt.setString(1, empS.getLastName());
            myStmt.setLong(2, empS.getDeptNumber());
            myStmt.setLong(3, empS.getVersion());
            myStmt.setLong(4, id);

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

        close();


    }

    public void deleteEmployee(Employee emp) {

        PreparedStatement myStmt;
        long id = emp.getId();
        System.out.println(id);

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

//        try {
//            myStmt = myConn.prepareStatement("DELETE FROM params/*(object_id, attr_id, text_value)\n*/" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095084373");
//            myStmt.setLong(1, id);
//
//            myStmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            myStmt = myConn.prepareStatement("DELETE FROM params/*(object_id, attr_id, text_value)\n*/" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095087262");
//            myStmt.setLong(1, id);
//
//            myStmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            myStmt = myConn.prepareStatement("DELETE FROM params/*(object_id, attr_id, text_value)\n*/" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095090681");
//            myStmt.setLong(1, id);
//
//            myStmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            myStmt = myConn.prepareStatement("DELETE FROM params/*(object_id, attr_id, text_value)\n*/" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095093511");
//            myStmt.setLong(1, id);
//
//            myStmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            myStmt = myConn.prepareStatement("DELETE FROM params /*(object_id, attr_id, text_value)\n*/" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1511095096402");
//            myStmt.setLong(1, id);
//
//            myStmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        close();

    }


//    public void close() throws SQLException {
//        myConn.close();
//    }

}
