package objects;

import cache.Cacheable;
import objects.types.EntitiesTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drake on 06/02/18.
 *
 * @author P.Pridorozhny
 */
public class Task implements Cacheable {

    private long id;
    private EntitiesTypes type;
    private String name;
    private int taskTime;
    private long version;
    private long projectID;
    private List<Sprint> sprints;

    public Task(long id) {
        type = EntitiesTypes.TASK;
        this.id = id;
        sprints = new ArrayList<>();
    }

    public Task() {
        type = EntitiesTypes.TASK;
        sprints = new ArrayList<>();
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
        Task task = new Task(id);

        task.setName(name);
        task.setVersion(version);
        task.setTaskTime(taskTime);
        task.setSprints(sprints);
        task.setProjectID(projectID);

        return task;
    }

    public void copy(Task task) {
        name = task.getName();
        version = task.getVersion();
        taskTime = task.getTaskTime();
        sprints = task.getSprints();
        projectID = task.getProjectID();
    }

    public void setType(EntitiesTypes type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + taskTime + " " + " " + version + "\n" + sprints;
    }
}
