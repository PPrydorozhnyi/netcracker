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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {

        return firstName + "\n" + lastName + "\n" + job + "\n" + hiredate.getTime() + "\n" + salary + "\n" +
                commission + "\n" + deptNumber + "\n" + department;
    }
}
