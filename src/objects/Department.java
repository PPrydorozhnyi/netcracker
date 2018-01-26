package objects;

import cache.Cacheable;
import objects.types.EntitiesTypes;

import java.util.ArrayList;

/**
 * @author P.Pridorozhny
 */
public class Department implements Cacheable {

    private long id;
    private String name;
    private String companyName;
    private String location;
    private ArrayList<Employee> employees = new ArrayList<>();
    private long version;
    private EntitiesTypes type;

    public Department() {
        type = EntitiesTypes.DEPARTMENT;
    }

    public Department(long id) {
        this.id = id;
        type = EntitiesTypes.DEPARTMENT;
    }

    @Override
    public long getId() {
        return id;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public EntitiesTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " " + companyName + " " + location + " " + version;
    }

    public void copy(Department dept) {
        name = dept.getName();
        companyName = dept.getCompanyName();
        location = dept.getLocation();
        employees = dept.getEmployees();
        version = dept.getVersion();
    }

}
