package com.example.healthhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button makeAppoinmnet = findViewById(R.id.button3);

        Button logout = findViewById(R.id.logoutButton);

        makeAppoinmnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent4 = new Intent(Options.this, TODO change to the name of the new activity **Options**+.class);
                //startActivity(intent4);
            }
        });









        //Log out will take user to main screen
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOut = new Intent(Options.this, MainActivity.class);
                startActivity(logOut);
            }
        });




    }


}