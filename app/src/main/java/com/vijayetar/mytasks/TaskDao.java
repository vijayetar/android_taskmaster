package com.vijayetar.mytasks;

import androidx.room.Dao;
import androidx.room.Insert;

import com.vijayetar.mytasks.models.Task;

@Dao // data base access object
public interface TaskDao {
    @Insert // the annotations describe the behavior, not the name of the method
    public void saveTask(Task task);
}
