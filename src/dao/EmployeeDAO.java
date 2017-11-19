package dao;

import objects.Employee;

import java.sql.*;

/**
 * @author P.Pridorozhny
 */
public class EmployeeDAO {

    private Connection myConn;

    public EmployeeDAO() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            myConn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "drakeNC", "drake");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

        }
    }

//    public void update(Employee emp) throws SQLException {
//
//        PreparedStatement myStmt = null;
//
//        myConn.prepareStatement();
//    }

    public void getByID(long id) throws SQLException {

        ResultSet rs = null;
        PreparedStatement myStmt = null;

        myStmt = myConn.prepareStatement("select objects.NAME nam, fi_name.TEXT_VALUE txt " +
                "from objects join object_types on " +
                "object_types.object_type_id = objects.object_type_id " +
                "join attr on attr.object_type_id = object_types.object_type_id " +
                "and attr.name='ENAME' " +
                "join params fi_name on fi_name.attr_id = attr.attr_id and " +
                "fi_name.object_id = objects.object_id " +
                "WHERE object_types.OBJECT_TYPE_ID = 1511093759755 and objects.OBJECT_ID = ?");
        myStmt.setLong(1, id);
        rs = myStmt.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("nam"));
            System.out.println(rs.getString("txt"));
        }
//                "join attr mid_name_attr on \n" +
//                "mid_name_attr.\"object_type_id\"=\"Object_types\".\"object_type_id\" and \n" +
//                "mi\n" +
//                "d_name_attr.\"name\"='mid_name'\n" +
//                "join \"Params\" mid_name on mid_name.\"attr_id\"=mid_name_attr.\"attr_id\" \n" +
//                "and mid_name.\"object_id\"=\"Objects\".\"object_id\"\n" +
//                "join \"Attributes\" sex_attr on \n" +
//                "sex_attr.\"object_type_id\"=\"Object_types\".\"object_type_id\" and \n" +
//                "sex_attr.\"name\"='s\n" +
//                "ex'\n" +
//                "join \"Params\" sex on sex.\"attr_id\"=sex_attr.\"attr_id\" and \n" +
//                "sex.\"object_id\"=\"Objects\".\"object_id\"\n" +
//                "join \"Attributes\" birth_attr on \n" +
//                "birth_attr.\"object_type_id\"=\"Object_types\".\"object_type_id\" and \n" +
//                "birth_attr.\"name\"='birthday'\n" +
//                "join \"Params\" birth on birth.\"a\n" +
//                "ttr_id\"=birth_attr.\"attr_id\" and \n" +
//                "birth.\"object_id\"=\"Objects\".\"object_id\"\n" +
//                "join \"Attributes\" pass_attr on \n" +
//                "pass_attr.\"object_type_id\"=\"Object_types\".\"object_type_id\" and \n" +
//                "pass_attr.\"name\"='passport'\n" +
//                "join \"Params\" pass on pass.\"attr_id\"=pass_attr.\"attr_id\" and \n" +
//                "pass.\"object_id\"=\"Objects\".\"object_id\"\n" +
//                "join \"Params\" people on \"Objects\".\"object_id\"=people.\"number_value\"\n" +
//                "join \"Objects\" reg on people.\"object_id\"=reg.\"object_id\"\n" +
//                "join \"Attributes\" aid_attr on \n" +
//                "aid_attr.\"object_type_id\"=reg.\"object_type_id\" and \n" +
//                "aid_attr.\"n\n" +
//                "ame\"='aid'\n" +
//                "join \"Params\" aid on aid.\"attr_id\"=aid_attr.\"attr_id\" and \n" +
//                "aid.\"object_id\"=reg.\"object_id\"\n" +
//                "join \"Attributes\" apt_attr on \n" +
//                "apt_attr.\"object_type_id\"=reg.\"object_type_id\" and \n" +
//                "apt_attr.\"name\"='apt'\n" +
//                "join \"Params\" apt on apt.\"attr_id\"=apt_attr.\"attr_id\n" +
//                "\" and \n" +
//                "apt.\"object_id\"=reg.\"object_id\"\n" +
//                "where \"Object_types\".\"name\"='people' and aid.\"number_value\"=11 and \n" +
//                "apt.\"number_value\"=3;");


    }

}
