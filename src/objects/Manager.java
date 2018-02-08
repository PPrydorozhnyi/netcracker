package objects;

import cache.Cacheable;
import objects.types.EntitiesTypes;

/**
 * Created by drake on 08/02/18.
 *
 * @author P.Pridorozhny
 */
public class Manager implements Cacheable {

    private long id;
    private EntitiesTypes type;
    private String lastName;
    private String firstName;
    private int salary;
    private long version;
    private long deptID;
    private long projectID;

    public Manager() {
        type = EntitiesTypes.MANAGER;
    }

    public Manager(long id) {
        this.id = id;
        type = EntitiesTypes.MANAGER;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getDeptID() {
        return deptID;
    }

    public void setDeptID(long deptID) {
        this.deptID = deptID;
    }

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void copy(Manager manager) {
        version = manager.getVersion();
        firstName = manager.getFirstName();
        lastName = manager.getLastName();
        deptID = manager.getDeptID();
        projectID = manager.getProjectID();
        salary = manager.getSalary();
    }

    @Override
    public EntitiesTypes getType() {
        return type;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Cacheable copyWithID() {

        Manager manager = new Manager(id);

        manager.setVersion(version);
        manager.setFirstName(firstName);
        manager.setLastName(lastName);
        manager.setDeptID(deptID);
        manager.setProjectID(projectID);
        manager.setSalary(salary);

        return manager;
    }

    @Override
    public String toString() {
        return id + " " + firstName + " " + lastName + " " + salary + " "+ projectID + " " + " " + version + "\n" + deptID;
    }
}
