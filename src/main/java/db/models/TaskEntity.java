package db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tasks")
public class TaskEntity {
    public static final String NAME_COLUMN = "nameTask";

    @DatabaseField(generatedId = true)
    private long taskId;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private ThemeEntity theme;

    @DatabaseField(canBeNull = false)
    private String taskType;

    @DatabaseField(canBeNull = false)
    private String nameTask;

    @DatabaseField(canBeNull = false)
    private Double maxPoint;

    public TaskEntity() {}

    public TaskEntity(ThemeEntity theme, String taskType, String nameTask, Double maxPoint) {
        this.theme = theme;
        this.taskType = taskType;
        this.nameTask = nameTask;
        this.maxPoint = maxPoint;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getTaskType() {
        return taskType;
    }
}
