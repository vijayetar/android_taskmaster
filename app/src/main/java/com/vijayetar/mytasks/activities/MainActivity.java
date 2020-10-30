package com.vijayetar.mytasks.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.ApiOperation;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.api.graphql.model.ModelSubscription;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.vijayetar.mytasks.R;
import com.vijayetar.mytasks.TaskAdapter;
import com.vijayetar.mytasks.models.Database;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnInteractWithTaskListener {
    Database db;
    RecyclerView recyclerView;
    ArrayList<Task> allMyTasks;
    Handler handler;


    @Override
    public void onResume(){
        super.onResume();
        // this is setting the username above the buttons in a textview or else get the preferences from the settings activity
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView declareUsername = findViewById(R.id.enterUserNameTextV);
        String fromPreferences = preferences.getString("userName", "Go to settings to enter username");
        declareUsername.setText(fromPreferences + "'s tasks"); //this is default string if username is not available
        //        ArrayList<Task> updatedTasks = (ArrayList<Task>) db.taskDao().getAllTasksReversed();
        //         need to iterate over the new array list and update the allMyTasks contents, because allMyTasks is already connected with the adaptor
//        for (int i = 0; i<updatedTasks.size(); i++){
//            allMyTasks.add(updatedTasks.get(i));
//        }

//        System.out.println("this is the allTasks size: "+allMyTasks.size());
//        if (allMyTasks.size()>0) {
//            recyclerView.getAdapter().notifyItemInserted(allMyTasks.size() - 1);
//            recyclerView.smoothScrollToPosition(allMyTasks.size() - 1);
//        }
//        //notifies other users on the app that item has been saved
        Handler handleSingleTaskAdded= new Handler(Looper.getMainLooper(),
                (message -> {
                    recyclerView.getAdapter().notifyItemInserted(allMyTasks.size()-1);
                    Toast.makeText(
                            this,
                            allMyTasks.get(allMyTasks.size()-1).getTitle()+" was added to task",
                            Toast.LENGTH_LONG).show();
                    recyclerView.smoothScrollToPosition(allMyTasks.size()-1);
                    return true;
                })
                );

        String TAG = "Amplify.subscription";
        ApiOperation subscription = Amplify.API.subscribe(
                ModelSubscription.onCreate(Task.class),
                onEstablished -> Log.i(TAG, "Subscription established"),
                onCreated -> {
                    Log.i(
                            TAG, " AMPLIFY SUBSCRIPTION - Task create subscription received: " + ((Task) onCreated.getData()).getTitle()
                    );
                    Task createdTask = (Task) onCreated.getData();
                    allMyTasks.add(createdTask);
                    handleSingleTaskAdded.sendEmptyMessage(1);
                },

                onFailure -> Log.e(TAG, "Subscription failed", onFailure),
                () -> Log.i(TAG, "Subscription completed")
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // configure AWS
        configureAWS();
        // configure database
        db = Room.databaseBuilder(getApplicationContext(),Database.class, "vijayetar_taskmaster")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        handler = new Handler(Looper.getMainLooper(),
                new Handler.Callback(){
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        recyclerView.getAdapter().notifyDataSetChanged();
                        return true;
                    }
                }
        );
        connectAdapterToRecyclerView();
        getContentFromAWSDynamoDB();

//        allMyTasks = (ArrayList<Task>) db.taskDao().getAllTasksReversed();
//        getContentFromAWSDynamoDB();


        allButtonsAndListeners();


    }
    @Override
    public void taskListener(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
        intent.putExtra("title", task.getTitle());
        intent.putExtra("body", task.getBody());
        intent.putExtra("state", task.getState());
        this.startActivity(intent);
    }

    private void configureAWS(){
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
    }

    private void connectAdapterToRecyclerView(){
        allMyTasks = new ArrayList<>();
        recyclerView = findViewById(R.id.allMyTasksRV);
        LinearLayoutManager l = new LinearLayoutManager(this);
//        l.canScrollHorizontally(); // to set it up horizontally, otherwise not required
//        l.setOrientation(LinearLayoutManager.HORIZONTAL);// set it up horizontally, otherwise not required
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(new TaskAdapter(allMyTasks, this));
    }

    // pull out the contents of the dynamoDB
    private void getContentFromAWSDynamoDB(){
        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    for (Task thisTask: response.getData() ){
                        allMyTasks.add(thisTask);
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("AmplifyDB.queryitems", "Could not get the items"));
    }

    private void allButtonsAndListeners(){
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


}