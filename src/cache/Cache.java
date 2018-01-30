package cache;

import dao.DepartmentDAO;
import dao.EmployeeDAO;
import objects.Department;
import objects.Employee;
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
    DepartmentDAO departmentDAO;

    private final static int MAXSIZE = 5;

    private Cache() {
        cacheMap = new ConcurrentHashMap<>();
        readWriteLock = new ReentrantReadWriteLock(true);
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();

        employeeDAO = new EmployeeDAO();
        departmentDAO = new DepartmentDAO();
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
                    default:
                        System.out.println("Something going wrong in Cache flush()");
                }
            }
        }
    }

}
