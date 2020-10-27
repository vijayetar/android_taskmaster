package com.vijayetar.mytasks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vijayetar.mytasks.R;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
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