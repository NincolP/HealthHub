package com.example.healthhub;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

        //Instantiating authorization object
        FirebaseAuth auth = FirebaseAuth.getInstance();


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(resetUserOrPass.this, "Password reset email has been sent. Check your email", Toast.LENGTH_LONG).show();
                                    //After reset email has been sent, user will be taken to the log in page.
                                    Intent login = new Intent(resetUserOrPass.this, Login.class);
                                }


                            }
                        });
            }
        });

    }

    //This will take the user back to main page, in case the remembered their credentials
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}