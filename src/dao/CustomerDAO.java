package dao;

import objects.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by drake on 09/02/18.
 *
 * @author P.Pridorozhny
 */
public class CustomerDAO extends DAO {

    public Customer getByID(long id) throws SQLException {
        Customer customer;

        ResultSet rs;
        PreparedStatement myStmt;

        openConnection();

        myStmt = myConn.prepareStatement("SELECT o.NAME nam,o.vers vers, attr.name attr_name, o.PARENT_ID par, attr.ATTR_ID attr_id, " +
                " p.TEXT_VALUE txt, p.NUMBER_VALUE nmbr, p.DATE_VALUE dt\n" +
                "FROM objects o\n" +
                "INNER JOIN attr ON attr.object_type_id = o.OBJECT_TYPE_ID " +
                "LEFT JOIN params p ON p.attr_id = ATTR.attr_id\n" +
                "  AND p.object_id = o.object_id" +
                " WHERE o.OBJECT_TYPE_ID = 1517314336024 AND o.OBJECT_ID = ?");
        myStmt.setLong(1, id);

        rs = myStmt.executeQuery();

        // TODO: check empty ResultSet
//        if (!rs.next()) {
//            close();
//            return null;
//        }


        customer = new Customer(id);
        extractTaskFromResultSet(customer, rs);

        close();

        return customer;
    }

    private void extractTaskFromResultSet(Customer customer, ResultSet rs) throws SQLException {

        long attr_id;

        while (rs.next()) {
            attr_id = rs.getLong("attr_id");

            customer.setVersion(rs.getLong("vers"));
            customer.setLastName(rs.getString("nam"));
            customer.setProjectID(rs.getLong("par"));

            if (attr_id == 1517315005886L) {
                customer.setFirstName(rs.getString("txt"));
            }
        }

    }

    public Customer createCustomer(Customer cst) {

        Customer customer;
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

        customer = new Customer(id);
        customer.copy(cst);


        try {
            myStmt = myConn.prepareStatement("INSERT INTO objects(object_id, OBJECT_TYPE_ID, name, parent_id, vers) " +
                    "VALUES(?, 1517314336024, ?, ?, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, cst.getLastName());
            myStmt.setLong(3, cst.getProjectID());
            myStmt.setLong(4, cst.getVersion());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("INSERT INTO params(object_id, attr_id, TEXT_VALUE)\n" +
                    "VALUES(?, 1517315005886, ?)");
            myStmt.setLong(1, id);
            myStmt.setString(2, cst.getFirstName());

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();

        return customer;
    }

    public void updateCustomer(Customer customer) {
        PreparedStatement myStmt;
        long id = customer.getId();

        openConnection();


        try {
            myStmt = myConn.prepareStatement("UPDATE objects " +
                    "SET  NAME = ?, parent_id = ?, vers = ?" +
                    " WHERE OBJECT_ID = ?");
            myStmt.setString(1, customer.getLastName());
            myStmt.setLong(2, customer.getProjectID());
            myStmt.setLong(3, customer.getVersion());
            myStmt.setLong(4, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            myStmt = myConn.prepareStatement("UPDATE params" +
                    " SET TEXT_VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1517315005886");
            myStmt.setString(1, customer.getFirstName() );
            myStmt.setLong(2, id);

            myStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();

    }

//    public void deleteCustomer(Customer customer) {
//        PreparedStatement myStmt;
//        long id = customer.getId();
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
