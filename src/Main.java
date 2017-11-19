import dao.EmployeeDAO;

import java.sql.SQLException;

/**
 * Created by drake on 19/11/17.
 */
public class Main {

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        try {
            dao.getByID(1511094532520L);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
