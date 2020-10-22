package com.vijayetar.mytasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
        ArrayList<Task> allMyTasks = new ArrayList<>();
        allMyTasks.add(new Task ("attend Java class", "Codefellows classes are fun", "assigned" ));
        allMyTasks.add(new Task("code challenges", "lab 27 and 28 are hard", "new"));
        allMyTasks.add(new Task("apply to jobs","apply to several jobs on glassdoor", "in progress"));

        RecyclerView recyclerView = findViewById(R.id.allMyTasksRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new TaskAdapter(Task,this));



        // this is button taking you to the add task and all tasks activity
        Button addTask = MainActivity.this.findViewById(R.id.addTask);
        Button allTasks = MainActivity.this.findViewById(R.id.allTasks);
        ImageButton settingsButton = MainActivity.this.findViewById(R.id.goToSettings);
        // addTask button that goes to a new intent
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.out.println("we are adding a task");
                Intent addATaskActivity = new Intent(MainActivity.this, AddTask.class);
                MainActivity.this.startActivity(addATaskActivity);
            }
        });
        // all tasks go to a new intent
        allTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.out.println("we are reviewing all tasks");
                // try to connect to all Tasks page
                Intent seeAllTasksActivity = new Intent(MainActivity.this, AllTasks.class);
                MainActivity.this.startActivity(seeAllTasksActivity);
            }
        });
        // settingsButton now going to settings page
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("going to settings page");
                Intent seeUserSettingsActivity = new Intent(MainActivity.this, UserSettings.class);
                MainActivity.this.startActivity(seeUserSettingsActivity);
            }
        });
    }
}