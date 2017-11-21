import dao.EmployeeDAO;
import objects.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author P.Pridorozhny
 */
public class Main {

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        Employee emp = null;
        ArrayList<Employee> employees = null;
        try {
            emp = dao.getByID(1511211821899L);
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

        //dao.createEmployee(emp);
        //dao.updateEmployee(employees.get(0), emp);
        //dao.deleteEmployee(employees.get(1));

        try {
            dao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(emp);

    }

}
