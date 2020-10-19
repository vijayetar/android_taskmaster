package com.vijayetar.mytasks;

import androidx.appcompat.app.AppCompatActivity;

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
            }
        });
        allTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.out.println("we are reviewing all tasks");
            }
        });
    }
}