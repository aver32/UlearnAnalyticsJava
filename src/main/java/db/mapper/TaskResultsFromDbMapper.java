package db.mapper;

import db.models.StudentEntity;
import db.models.TaskResultsEntity;
import models.Student;
import models.TaskResult;

public class TaskResultsFromDbMapper {
    public static TaskResult map(TaskResultsEntity taskResults) {
        return new TaskResult(taskResults.getCurrentPoints(), StudentFromDbMapper.map(taskResults.getStudent()));
    }
}
