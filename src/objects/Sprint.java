package objects;

import objects.types.EntitiesTypes;

/**
 * Created by drake on 06/02/18.
 *
 * @author P.Pridorozhny
 */
public class Sprint {

    private long id;
    private EntitiesTypes type;
    private int difficulty;
    private long version;
    private String name;
    private long taskID;

    public Sprint(long id) {
        type = EntitiesTypes.SPRINT;
        this.id = id;
    }

    public Sprint() {
        type = EntitiesTypes.SPRINT;
    }

    public long getId() {
        return id;
    }

    public EntitiesTypes getType() {
        return type;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
    }

    public void copy(Sprint sprint) {
        difficulty = sprint.getDifficulty();
        version = sprint.getVersion();
        name = sprint.getName();
        taskID = sprint.getTaskID();
    }
}
