package com.example.healthhub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class resetUserOrPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_user_or_pass);

        //To display back arrow that can take user back to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mapping layout components
        EditText email = findViewById(R.id.emailAddress);
        Button resetPassword = findViewById(R.id.resetPass);

        //Instantiating a database object to access database.
        FirebaseFirestore db = FirebaseFirestore.getInstance();




    }

    //This will take the user back to main page, in case the remembered their credentials
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}