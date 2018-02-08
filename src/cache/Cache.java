package cache;

import dao.*;
import objects.*;
import objects.types.EntitiesTypes;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author P.Pridorozhny
 */
public class Cache {


    private Map<Long, Cacheable> cacheMap;
    private static Cache instance;
    private final ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;

    private EmployeeDAO employeeDAO;
    private DepartmentDAO departmentDAO;
    private SprintDAO sprintDAO;
    private TaskDAO taskDAO;
    private ProjectDAO projectDAO;
    private ManagerDAO managerDAO;

    private final static int MAXSIZE = 0;

    private Cache() {
        cacheMap = new ConcurrentHashMap<>();
        readWriteLock = new ReentrantReadWriteLock(true);
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();

        employeeDAO = new EmployeeDAO();
        departmentDAO = new DepartmentDAO();
        sprintDAO = new SprintDAO();
        taskDAO = new TaskDAO();
        projectDAO = new ProjectDAO();
        managerDAO = new ManagerDAO();
    }

    public static Cache getCache() {
        if (instance == null)
            instance = new Cache();

        return instance;
    }

    public void create(Cacheable object) {

        Cacheable entity = null;
        writeLock.lock();

        switch (object.getType()) {
            case DEPARTMENT:
                entity = departmentDAO.createDepartment((Department) object);
                break;
            case EMPLOYEE:
                entity = employeeDAO.createEmployee((Employee) object);
                //System.out.println(object);
                break;
            case SPRINT:
                entity = sprintDAO.createSprint((Sprint) object);
                break;
            case TASK:
                entity = taskDAO.createTask((Task) object);
                break;
            case PROJECT:
                entity = projectDAO.createProject((Project) object);
                break;
            case MANAGER:
                entity = managerDAO.createManager((Manager) object);
                break;
            default:
                System.out.println("Something going wrong in Cache create()");
        }

//        System.out.println(entity.getId());
        cacheMap.put(entity.getId(), entity);
//        System.out.println(cacheMap);
        flush();

        writeLock.unlock();


    }

    public Cacheable get(Long id, EntitiesTypes type) {

        readLock.lock();

        Cacheable obj = cacheMap.get(id);

        if (obj == null) {

            switch (type) {
                case DEPARTMENT:
                    try {
                        obj = departmentDAO.getByID(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case EMPLOYEE:
                    try {
                        obj = employeeDAO.getByID(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case SPRINT:
                    try {
                        obj = sprintDAO.getByID(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case TASK:
                    try {
                        obj = taskDAO.getByID(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case PROJECT:
                    try {
                        obj = projectDAO.getByID(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case MANAGER:
                    try {
                        obj = managerDAO.getByID(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Something going wrong in Cache get()");
            }

            cacheMap.put(obj.getId(), obj);

        }

        flush();

        readLock.unlock();

        return obj.copyWithID();

    }

    public void update(Cacheable entity) {
        writeLock.lock();

        cacheMap.put(entity.getId(), entity);

//        switch (entity.getType()) {
//            case DEPARTMENT:
//                departmentDAO.updateDepartment((Department) entity);
//                break;
//            case EMPLOYEE:
//                employeeDAO.updateEmployee((Employee) entity);
//                break;
//            default:
//                System.out.println("Something going wrong in Cache update()");
//        }

        flush();

        writeLock.unlock();
    }

    public void delete(Cacheable entity) {
        readLock.lock();

        cacheMap.remove(entity.getId());

        switch (entity.getType()) {
            case DEPARTMENT:
                departmentDAO.deleteDepartment((Department) entity);
                break;
            case EMPLOYEE:
                employeeDAO.deleteEmployee((Employee) entity);
                break;
            case SPRINT:
                sprintDAO.deleteSprint((Sprint) entity);
                break;
            case TASK:
                taskDAO.deleteTask((Task) entity);
                break;
            case PROJECT:
                projectDAO.deleteProject((Project) entity);
                break;
            case MANAGER:
                managerDAO.deleteManager((Manager) entity);
                break;
            default:
                System.out.println("Something going wrong in Cache delete()");
        }

        readLock.unlock();
    }

    private void flush() {
        if (cacheMap.size() > MAXSIZE) {
            for (Map.Entry<Long, Cacheable> pair : cacheMap.entrySet()) {
                switch (pair.getValue().getType()) {
                    case EMPLOYEE:
                        employeeDAO.updateEmployee((Employee) pair.getValue());
                        break;
                    case DEPARTMENT:
                        departmentDAO.updateDepartment((Department) pair.getValue());
                        break;
                    case SPRINT:
                        sprintDAO.updateSprint((Sprint) pair.getValue());
                        break;
                    case TASK:
                        taskDAO.updateTask((Task) pair.getValue());
                        break;
                    case PROJECT:
                        projectDAO.updateProject((Project) pair.getValue());
                        break;
                    case MANAGER:
                        managerDAO.updateManager((Manager) pair.getValue());
                        break;
                    default:
                        System.out.println("Something going wrong in Cache flush()");
                }
            }
        }
    }

}
