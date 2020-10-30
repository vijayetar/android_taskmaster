package com.vijayetar.mytasks.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.api.ApiOperation;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelSubscription;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.vijayetar.mytasks.R;
import com.vijayetar.mytasks.models.Database;

public class AddTaskActivity extends AppCompatActivity {
    Database db;
    NotificationChannel channel;
    NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        db = Room.databaseBuilder(getApplicationContext(), Database.class, "vijayetar_taskmaster")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        channel = new NotificationChannel("basic", "basic", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("basic notifications");
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Button addTask = AddTaskActivity.this.findViewById(R.id.addTaskSubmit);

        addTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("about to get the contents");
                EditText taskTitle = AddTaskActivity.this.findViewById(R.id.editTaskTitle);
                EditText taskDescription = AddTaskActivity.this.findViewById(R.id.editTaskDescription);
                String taskName = taskTitle.getText().toString();
                String taskBody = taskDescription.getText().toString();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(AddTaskActivity.this, "basic")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(taskName)
                        .setContentText("Adding Task " + taskName)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line..."))
                        .setPriority(NotificationCompat.PRIORITY_MAX);

                notificationManager.notify(89898989, builder.build());

                //creating it as the newTask
                Task newTask = Task.builder()
                        .title(taskName).body(taskBody)
                        .state("assigned").build();

                // let us save the task to the database instead
                db.taskDao().saveTask(newTask);

                // saving it to DynamoDB
                Amplify.API.mutate(ModelMutation.create(newTask),
                        response -> Log.i("AmplifyDB","saved to database"),
                        error -> Log.e("AmplifyDB",error.toString()));

                // try to print out the submitted text view here on click
                TextView textShowSubmit = AddTaskActivity.this.findViewById(R.id.editShowSubmit);
                textShowSubmit.setVisibility(View.VISIBLE);

                finish(); // TAKES YOU BACK TO THE PREVIOUS PAGE

            }
        });

    }
}