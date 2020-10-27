package com.vijayetar.mytasks.models;

import androidx.room.RoomDatabase;

import com.vijayetar.mytasks.TaskDao;

@androidx.room.Database(entities = {Task.class}, version=1)
public abstract class Database extends RoomDatabase {

    public abstract TaskDao taskDao();
}
