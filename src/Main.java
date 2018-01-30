import cache.Cache;
import objects.Department;
import objects.Employee;
import objects.types.EntitiesTypes;

/**
 * @author P.Pridorozhny
 */
public class Main {

    public static void main(String[] args) {


        Cache cache = Cache.getCache();
        Employee emp = null;
        Department department = null;

//        emp = (Employee) cache.get(1511094532520L, EntitiesTypes.EMPLOYEE);
//        System.out.println(emp);
//        cache.create(emp);
//        System.out.println(emp.getId());
//        cache.delete(emp);
//        emp.setVersion(3);
//        cache.update(emp);
        department = (Department) cache.get(1511096690819L, EntitiesTypes.DEPARTMENT);
        System.out.println(department);


//        EmployeeDAO dao = new EmployeeDAO();
//
////        ArrayList<Employee> employees = null;
//        try {
//            emp = dao.getByID(1511094532520L);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//
//        try {
//            employees = dao.getByLastName("CLARK");
//            System.out.println(employees.get(0));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (employees != null) {
//            for (Employee empl : employees)
//                System.out.println(empl);
//        }

//        dao.createEmployee(emp);
//        dao.updateEmployee(employees.get(0), emp);
//        dao.deleteEmployee(emp);

//        dao.close();


//        System.out.println(emp);
//        System.out.println(department);
//
//        DepartmentDAO dao = new DepartmentDAO();
//        Department department = null;
//        Department deptD = null;
//        ArrayList<Department> departments = new ArrayList<>();

//        try {
//            department = dao.getByID(1511096690819L);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        dao.createDepartment(department);
//
//       System.out.println(department);
//
//        if (department != null)
//            for (Employee empl : department.getEmployees())
//                System.out.println(empl);
//
//        try {
//            departments = dao.getByDeptName("RESEARCH");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        try {
//            deptD = dao.getByID(1511687032041L);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        dao.updateDepartment(department, deptD);
       // dao.deleteDepartment(deptD);

//        if (departments != null) {
//            for (Department dept : departments) {
//                System.out.println(dept);
//                System.out.println(dept.getEmployees());
//            }
//        }

//        dao.close();

    }

}
