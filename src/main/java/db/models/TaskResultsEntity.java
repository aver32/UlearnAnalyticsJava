package db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import models.Task;

@DatabaseTable(tableName = "taskResults")
public class TaskResultsEntity {
    @DatabaseField(generatedId = true)
    private long taskResultsId;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private TaskEntity task;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private StudentEntity student;

    @DatabaseField(canBeNull = false)
    private Double currentPoints;

    public TaskResultsEntity() {}

    public TaskResultsEntity(TaskEntity task, StudentEntity student, Double currentPoints) {
        this.task = task;
        this.student = student;
        this.currentPoints = currentPoints;
    }

    public long getTaskId() {
        return task.getTaskId();
    }

    public TaskEntity getTask() {
        return task;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public Double getCurrentPoints() {
        return currentPoints;
    }
}
