package com.vijayetar.mytasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addTask = MainActivity.this.findViewById(R.id.addTask);
        Button allTasks = MainActivity.this.findViewById(R.id.allTasks);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.out.println("we are adding a task");
                Intent addATaskActivity = new Intent(MainActivity.this, AddTask.class);
                MainActivity.this.startActivity(addATaskActivity);
            }
        });
        allTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.out.println("we are reviewing all tasks");
                // try to connect to all Tasks page
                Intent seeAllTasksActivity = new Intent(MainActivity.this, AllTasks.class);
                MainActivity.this.startActivity(seeAllTasksActivity);
            }
        });
    }
}