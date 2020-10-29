package com.vijayetar.mytasks;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.amplifyframework.datastore.generated.model.Task;

import java.util.List;

@Dao // database access object
public interface TaskDao {
    @Insert // the annotations describe the behavior, not the name of the method
    public void saveTask(Task task);

    @Query("SELECT * FROM Task;")
    public List<Task> getAllTasks();

    @Query("SELECT * FROM Task ORDER BY id DESC")
    public List<Task> getAllTasksReversed();
}
