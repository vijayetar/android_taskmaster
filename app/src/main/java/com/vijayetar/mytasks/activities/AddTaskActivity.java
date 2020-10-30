package com.vijayetar.mytasks.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.vijayetar.mytasks.R;
import com.vijayetar.mytasks.models.Database;

public class AddTaskActivity extends AppCompatActivity {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        db = Room.databaseBuilder(getApplicationContext(), Database.class, "vijayetar_taskmaster")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        Button addTask = AddTaskActivity.this.findViewById(R.id.addTaskSubmit);

        addTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("about to get the contents");
                EditText taskTitle = AddTaskActivity.this.findViewById(R.id.editTaskTitle);
                EditText taskDescription = AddTaskActivity.this.findViewById(R.id.editTaskDescription);
                String taskName = taskTitle.getText().toString();
                String taskBody = taskDescription.getText().toString();

                //save it to the DyanamoDB
                Task newTask = Task.builder().title(taskName).body(taskBody).state("assigned").build();
                Amplify.API.mutate(ModelMutation.create(newTask), response -> Log.i("AmplifyDB","saved to database"), error -> Log.e("AmplifyDB",error.toString()));


                // let us save the task to the database instead
                db.taskDao().saveTask(newTask);

                // try to print out the submitted text view here on click
                TextView textShowSubmit = AddTaskActivity.this.findViewById(R.id.editShowSubmit);
                textShowSubmit.setVisibility(View.VISIBLE);

                finish(); // TAKES YOU BACK TO THE PREVIOUS PAGE

            }
        });

    }
}