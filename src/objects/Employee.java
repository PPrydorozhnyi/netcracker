package objects;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author P.Pridorozhny
 */
public class Employee {

    private long id;
    private String firstName;
    private String lastName;
    private String job;
    private Calendar hiredate;
    private int salary;
    private int commission;
    private long deptNumber;
    private Department department;
    private long version;

    public Employee(long id) {
        this.id = id;
        hiredate = new GregorianCalendar();
    }

    public long getId() {
        return id;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public Employee() {
        hiredate = new GregorianCalendar();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Calendar getHiredate() {
        return hiredate;
    }

    public void setHiredate(Calendar hiredate) {
        this.hiredate = hiredate;
    }

    public void setHiredate(Date date) {
        this.hiredate.setTime(date);
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public long getDeptNumber() {
        return deptNumber;
    }

    public void setDeptNumber(long deptNumber) {
        this.deptNumber = deptNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {

        return firstName + "\n" + lastName + "\n" + job + "\n" + hiredate.getTime() + "\n" + salary + "\n" +
                commission + "\n" + deptNumber + "\n" + department + "\n" + version;
    }

    public void copy(Employee employee) {
        department = employee.getDepartment();
        deptNumber = employee.getDeptNumber();
        commission = employee.getCommission();
        salary = employee.getSalary();
        firstName = employee.getFirstName();
        lastName = employee.getLastName();
        job = employee.getJob();
        hiredate = employee.getHiredate();
        version = employee.getVersion();
    }
}
