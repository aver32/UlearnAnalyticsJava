package db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db.models.StudentEntity;
import db.models.TaskEntity;
import db.models.TaskResultsEntity;
import db.models.ThemeEntity;
import models.Student;
import models.Task;
import models.Theme;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DbOrmRepository {
    private static ConnectionSource connectionSource = null;
    private static final String URL = "jdbc:sqlite:C:\\Users\\flyli\\IdeaProjects\\UlearnAnalyticsJava\\db\\test.db";
    private Dao<StudentEntity, String> studentDao = null;
    private Dao<ThemeEntity, String> themeDao = null;
    private Dao<TaskResultsEntity, String> taskResultDao = null;
    private Dao<TaskEntity, String> taskDao = null;

    public void connect() throws SQLException {
        connectionSource = new JdbcConnectionSource(URL);

        studentDao = DaoManager.createDao(connectionSource, StudentEntity.class);
        themeDao = DaoManager.createDao(connectionSource, ThemeEntity.class);
        taskDao = DaoManager.createDao(connectionSource, TaskEntity.class);
        taskResultDao = DaoManager.createDao(connectionSource, TaskResultsEntity.class);
    }

    public void createStudentsTable() throws SQLException {
        TableUtils.createTable(connectionSource, StudentEntity.class);
    }

    public void createTasksTable() throws SQLException {
        TableUtils.createTable(connectionSource, TaskEntity.class);
    }

    public void createThemesTable() throws SQLException {
        TableUtils.createTable(connectionSource, ThemeEntity.class);
    }

    public void createTaskResultsTable() throws SQLException {
        TableUtils.createTable(connectionSource, TaskResultsEntity.class);
    }

    public void saveTasks(List<Task> tasks) throws SQLException {
        for (Task task : tasks) {
            ThemeEntity themeEntity = themeDao
                    .queryBuilder()
                    .where()
                    .eq(ThemeEntity.NAME_COLUMN,
                    task.getThemeName()).queryForFirst();

            taskDao.create(new TaskEntity(themeEntity,
                    task.getTaskType().toString(),
                    task.getNameTask(),
                    task.getMaxPoint()));
        }
    }

    public List<StudentEntity> getStudents() throws SQLException {
        return studentDao.queryForAll();
    }

    public List<TaskResultsEntity> getTaskResults() throws SQLException {
        return taskResultDao.queryForAll();
    }


    public void saveTaskResults(List<Task> tasks) throws SQLException {
        for (var task : tasks) {
            for (var taskResult : task.getTaskResults()) {
                TaskEntity taskEntity = taskDao
                        .queryBuilder()
                        .where()
                        .eq(TaskEntity.NAME_COLUMN, task.getNameTask())
                        .and()
                        .eq("taskType", task.getTaskType().toString())
                        .and()
                        .eq("maxPoint", task.getMaxPoint())
                        .queryForFirst();
                StudentEntity studentEntity = studentDao
                        .queryBuilder()
                        .where()
                        .eq(StudentEntity.NAME_COLUMN, taskResult.getCurrentStudent().getNameAndSurname())
                        .queryForFirst();
                if (taskEntity == null || studentEntity == null) {
                    continue;
                }
                taskResultDao.create(new TaskResultsEntity(taskEntity, studentEntity, taskResult.getCurrentTaskResult()));
            }
        }
    }


    public void saveThemes(Set<Theme> themes) throws SQLException {
        for (Theme theme : themes) {
            themeDao.create(new ThemeEntity(theme.getNameTheme()));
        }
    }

    public void saveStudents(List<Student> students) throws SQLException {
        for (Student student : students) {
            studentDao.create(new StudentEntity(student.getNameAndSurname(), student.getGroup(), student.getCity()));
        }
    }

    public void close() throws Exception {
        connectionSource.close();
    }
}
