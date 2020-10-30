package com.vijayetar.mytasks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.vijayetar.mytasks.R;

public class UserSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); // this is a reader
        final SharedPreferences.Editor preferenceEditor= preferences.edit();

        findViewById(R.id.saveNameButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameOfUser = UserSettingsActivity.this.findViewById(R.id.nameInputText);
                System.out.println(nameOfUser.getText().toString());
                // this sets a key value pair
                preferenceEditor.putString("userName",nameOfUser.getText().toString()); // you can store multiple types here
                preferenceEditor.apply();
//                Toast toast = Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG);
//                toast.show();
                finish();
            }
        });

    }
}