package com.example.healthhub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class resetUserOrPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_user_or_pass);

        //To display back arrow that can take user back to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //This will take the user back to main page, in case the remembered their credentials
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}