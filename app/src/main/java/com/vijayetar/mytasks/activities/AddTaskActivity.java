package com.vijayetar.mytasks.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vijayetar.mytasks.R;
import com.vijayetar.mytasks.models.Database;
import com.vijayetar.mytasks.models.Task;

public class AddTaskActivity extends AppCompatActivity {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        db = Room.databaseBuilder(getApplicationContext(), Database.class, "vijayetar_taskmaster").allowMainThreadQueries().build();
        Button addTask = AddTaskActivity.this.findViewById(R.id.addTaskSubmit);
//        final EditText textShowSubmit = AddTask.this.findViewById(R.id.editShowSubmit);

        addTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("about to get the contents");
                EditText taskTitle = AddTaskActivity.this.findViewById(R.id.editTaskTitle);
                EditText taskDescription = AddTaskActivity.this.findViewById(R.id.editTaskDescription);
                String taskName = taskTitle.getText().toString();
                String taskBody = taskDescription.getText().toString();
                System.out.println(String.format("This is the task %s : %s", taskName, taskBody));
                // let us save the task to the database instead
                Task newTask = new Task(taskName, taskBody, "assigned");
                db.taskDao().saveTask(newTask);
                // try to print out the submitted text view here on click
                TextView textShowSubmit = AddTaskActivity.this.findViewById(R.id.editShowSubmit);
                textShowSubmit.setVisibility(View.VISIBLE);

//                Toast toast = Toast.makeText(this,'s', Toast.LENGTH_LONG);
//                toast.setGravity(220,0, 1000);
//                toast.show();

            }
        });

    }
}