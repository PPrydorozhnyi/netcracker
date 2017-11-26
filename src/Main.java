import dao.DepartmentDAO;
import objects.Department;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author P.Pridorozhny
 */
public class Main {

    public static void main(String[] args) {
//        EmployeeDAO dao = new EmployeeDAO();
//        Employee emp = null;
//        ArrayList<Employee> employees = null;
//        try {
//            emp = dao.getByID(1511094532520L);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            employees = dao.getByLastName("WARD");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (employees != null) {
//            for (Employee empl : employees)
//                System.out.println(empl);
//        }

        //dao.createEmployee(emp);
        //dao.updateEmployee(employees.get(0), emp);
        //dao.deleteEmployee(employees.get(1));

//        dao.close();


//        System.out.println(emp);
//
        DepartmentDAO dao = new DepartmentDAO();
        Department department = null;
        ArrayList<Department> departments = new ArrayList<>();

//        try {
//            department = dao.getByID(1511096658079L);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(department);
//
//        if (department != null)
//            for (Employee empl : department.getEmployees())
//                System.out.println(empl);

        try {
            departments = dao.getByDeptName("ACCOUNTING");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (departments != null) {
            for (Department dept : departments) {
                System.out.println(dept);
                System.out.println(dept.getEmployees());
            }
        }

        dao.close();

    }

}
