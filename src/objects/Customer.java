package objects;

import cache.Cacheable;
import objects.types.EntitiesTypes;

/**
 * Created by drake on 09/02/18.
 *
 * @author P.Pridorozhny
 */
public class Customer implements Cacheable {

    private long id;
    private EntitiesTypes type;
    private String lastName;
    private String firstName;
    private long version;
    private long projectID;

    public Customer(long id) {
        this. id = id;
        type = EntitiesTypes.CUSTOMER;
    }

    public Customer() {
        type = EntitiesTypes.CUSTOMER;
    }


    public void setType(EntitiesTypes type) {
        this.type = type;
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

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public void copy(Customer customer) {
        version = customer.getVersion();
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        projectID = customer.getProjectID();
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

        Customer customer = new Customer(id);

        customer.setVersion(version);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setProjectID(projectID);

        return customer;
    }

    @Override
    public String toString() {
        return id + " " + firstName + " " + lastName + " " + version + " " + projectID;
    }
}
