package com.vijayetar.mytasks.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vijayetar.mytasks.R;
import com.vijayetar.mytasks.TaskAdapter;
import com.vijayetar.mytasks.models.Database;
import com.vijayetar.mytasks.models.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnInteractWithTaskListener {
    Database db;

    @Override
    public void onResume(){
        super.onResume();
        // this is setting the username above the buttons in a textview or else get the preferences from the settings activity
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView declareUsername = findViewById(R.id.enterUserNameTextV);
        String fromPreferences = preferences.getString("userName", "Go to settings to enter username");
        declareUsername.setText(fromPreferences + "'s tasks"); //this is default string if username is not available
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),Database.class, "vijayetar_taskmaster")
                .allowMainThreadQueries()
                .build();


        Task trialTask = new Task("trial", "this is a trial task", "assigned");
        db.taskDao().saveTask(trialTask);

        ArrayList<Task> allMyTasks = (ArrayList<Task>) db.taskDao().getAllTasks();

        RecyclerView recyclerView = findViewById(R.id.allMyTasksRV);
        LinearLayoutManager l = new LinearLayoutManager(this);
//        l.canScrollHorizontally(); // to set it up horizontally, otherwise not required
//        l.setOrientation(LinearLayoutManager.HORIZONTAL);// set it up horizontally, otherwise not required
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(new TaskAdapter(allMyTasks, this));


        // this is button taking you to the add task and all tasks activity
        Button addTask = MainActivity.this.findViewById(R.id.addTask);
        Button allTasks = MainActivity.this.findViewById(R.id.allTasks);
        ImageButton settingsButton = MainActivity.this.findViewById(R.id.goToSettings);
        // addTask button that goes to a new intent
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.out.println("we are adding a task");
                Intent addATaskActivity = new Intent(MainActivity.this, AddTaskActivity.class);
                MainActivity.this.startActivity(addATaskActivity);
            }
        });
        // all tasks go to a new intent
        allTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.out.println("we are reviewing all tasks");
                // try to connect to all Tasks page
                Intent seeAllTasksActivity = new Intent(MainActivity.this, AllTasksActivity.class);
                MainActivity.this.startActivity(seeAllTasksActivity);
            }
        });
        // settingsButton now going to settings page
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("going to settings page");
                Intent seeUserSettingsActivity = new Intent(MainActivity.this, UserSettingsActivity.class);
                MainActivity.this.startActivity(seeUserSettingsActivity);
            }
        });
    }
    @Override
    public void taskListener(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
        intent.putExtra("title", task.getTitle());
        intent.putExtra("body", task.getBody());
        intent.putExtra("state", task.getState());
        this.startActivity(intent);
    }

}