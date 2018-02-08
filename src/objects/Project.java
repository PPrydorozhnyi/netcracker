package objects;

import cache.Cacheable;
import objects.types.EntitiesTypes;

import java.util.*;

/**
 * Created by drake on 08/02/18.
 *
 * @author P.Pridorozhny
 */
public class Project implements Cacheable {

    private long id;
    private EntitiesTypes type;
    private String name;
    private Calendar start;
    private Calendar end;
    private long version;
    private long managerID;
    private List<Task> tasks;

    public Project() {
        start = new GregorianCalendar();
        end = new GregorianCalendar();
        type = EntitiesTypes.PROJECT;
        tasks = new ArrayList<>();
    }

    public Project(long id) {
        this.id = id;
        start = new GregorianCalendar();
        end = new GregorianCalendar();
        type = EntitiesTypes.PROJECT;
        tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public void setStart(Date date) {
        this.start.setTime(date);
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public void setEnd(Date date) {
        this.end.setTime(date);
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getManagerID() {
        return managerID;
    }

    public void setManagerID(long managerID) {
        this.managerID = managerID;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void copy(Project project) {
        name = project.getName();
        version = project.getVersion();
        managerID = project.getManagerID();
        start = project.getStart();
        end = project.getEnd();
        tasks = project.getTasks();
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

        Project project = new Project(id);

        project.setName(name);
        project.setVersion(version);
        project.setManagerID(managerID);
        project.setStart(start);
        project.setEnd(end);
        project.setTasks(tasks);

        return project;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + " " + managerID + " "
                + start.getTimeInMillis() + " " + end.getTimeInMillis() + " " + " " + version + "\n" + tasks;
    }
}
