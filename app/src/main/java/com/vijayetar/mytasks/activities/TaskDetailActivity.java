package com.vijayetar.mytasks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.vijayetar.mytasks.R;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent intent = getIntent();
        String titleName = intent.getStringExtra("title");
        String bodyName= intent.getStringExtra("body");
        String stateName = intent.getStringExtra("state");
        TextView title = findViewById(R.id.taskDetailNameTV);
        title.setText(titleName);
    }
}