package com.vijayetar.mytasks.models;

import androidx.room.RoomDatabase;

import com.amplifyframework.datastore.generated.model.Task;
import com.vijayetar.mytasks.TaskDao;

@androidx.room.Database(entities = {Task.class}, version=2)
public abstract class Database extends RoomDatabase {

    public abstract TaskDao taskDao();
}
