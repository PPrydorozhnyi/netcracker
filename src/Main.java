import dao.EmployeeDAO;
import objects.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by drake on 19/11/17.
 */
public class Main {

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        Employee emp = null;
        ArrayList<Employee> employees = null;
        try {
            emp = dao.getByID(1511094532520L);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            employees = dao.getByLastName("WARD");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (employees != null) {
            for (Employee empl : employees)
                System.out.println(empl);
        }

        try {
            dao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(emp);
    }

}
