import cache.Cache;
import objects.*;

/**
 * @author P.Pridorozhny
 */
public class Main {

    public static void main(String[] args) {


        Cache cache = Cache.getCache();
        Employee emp = null;
        Department department = null;
        Sprint sprint = null;
        Task task = null;
        Project project = null;
        Manager manager = null;
        Customer customer = null;

//        emp = (Employee) cache.get(1511094532520L, EntitiesTypes.EMPLOYEE);
//        System.out.println(emp);
//        cache.create(emp);
//        System.out.println(emp.getId());
//        cache.delete(emp);
//        emp.setVersion(3);
//        cache.update(emp);
//        department = (Department) cache.get(1511096690819L, EntitiesTypes.DEPARTMENT);
//        sprint = (Sprint) cache.get(1517942629304L, EntitiesTypes.SPRINT);
//        cache.create(sprint);
//        sprint.setDifficulty(7);
//        sprint.setName("nn");
//        sprint.setVersion(4);
//        sprint.setTaskID(19);
//        cache.update(sprint);
//        cache.delete(sprint);

//        task = (Task) cache.get(1517950499737L, EntitiesTypes.TASK);

//        cache.create(task);
//        task.setName("nn");
//        task.setTaskTime(80);
//
//        cache.update(task);

//        cache.delete(task);


//        project = (Project) cache.get(1518107072648L, EntitiesTypes.PROJECT);
//
////        cache.create(project);
////        project.setName("das");
////        project.setVersion(43);
////        project.setManagerID(1518107072648L);
////        project.setStart(new GregorianCalendar());
////        project.setEnd(new GregorianCalendar());
////
////        cache.update(project);
//
//        cache.delete(project);

//                manager = (Manager) cache.get(1518111982065L, EntitiesTypes.MANAGER);

//        department = (Department) cache.get(1511096664981L, EntitiesTypes.DEPARTMENT);
//        cache.create(manager);
//        manager.setFirstName("das");
//        manager.setLastName("sdas");
//        manager.setVersion(43);
//        manager.setDeptID(1518112036684L);
//        manager.setSalary(423);
//        project.setStart(new GregorianCalendar());
//        project.setEnd(new GregorianCalendar());
//
//        cache.update(manager);

//        cache.delete(manager);

//        customer = (Customer) cache.get(1517316012854L, EntitiesTypes.CUSTOMER);

//        cache.create(customer);
//        cache.delete(customer);
//
//        System.out.println(customer);


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
