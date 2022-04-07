package com.example.healthhub;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.MotionEffect;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstName,lastName, dob, address, unitNum, city, state,zipCode,email, password;
    User user;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //To display back arrow that can take user back to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        //FirebaseUser user;
        //create a database instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        firstName =findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        dob = findViewById(R.id.dateOfBirth);
        address = findViewById(R.id.address);
        unitNum = findViewById(R.id.unitNum);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        zipCode = findViewById(R.id.zipcode);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //Register button
        Button register = findViewById(R.id.button2);





        //button action
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               user = new User();
               user.setFirstName(firstName.getText().toString());
               user.setLastName(lastName.getText().toString());
               user.setDateOfBirth(dob.getText().toString());
               user.setUnitNumber(unitNum.getText().toString());
               user.setCity(city.getText().toString());
               user.setState(state.getText().toString());
               user.setZipCode(zipCode.getText().toString());
               user.setEmail(email.getText().toString());
               user.setPassword(password.getText().toString());


               //Checking if password is valid
               //At least one capital,lower case, special character and one digit
               if(user.validPassword()) {
                   //An email will be send to the user if registration is successful.
                  auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                auth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.d(TAG, "Registration email sent.");
                                                Toast.makeText(RegistrationActivity.this, "Registration successful. Confirmation email sent. Check your email.", Toast.LENGTH_LONG).show();
                                                DocumentReference newUser = db.collection("Users").document(auth.getUid());
                                                //user.setUserId(newUser.getId());
                                                newUser.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {
                                                            Log.d(TAG, "Data successfully saved in database");
                                                            newUser.update("password", "***********");
                                                            Intent reg = new Intent(RegistrationActivity.this, Options.class);
                                                            startActivity(reg);
                                                        }

                                                        else {
                                                        Log.w(MotionEffect.TAG, "Registration failure", task.getException());
                                                        }

                                                    }
                                                });
                                            }
                                        });
                            }
                            else {
                                Log.w(MotionEffect.TAG, "User registration failed", task.getException());
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                       }
                   });
                   //Successful registration would Insert java object into firebase database

               }

               //User will get a warning if password does not have at least one fo the following:
               //Uppercase, lowercase, special character and number.
               else {
                   password.setError("Invalid password. Enter at least one of the following: uppercase,lower case," +
                           "special character, number. Password length must be least eight characters");
                   password.requestFocus();
               }





           }
       });


    }




    //This will take the user back to main page, in case the remember they are already registered
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}