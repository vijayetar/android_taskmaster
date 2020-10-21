package com.vijayetar.mytasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        findViewById(R.id.saveNameButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameOfUser = UserSettings.this.findViewById(R.id.nameInputText);
                System.out.println(nameOfUser.getText().toString());
            }
        });

    }
}