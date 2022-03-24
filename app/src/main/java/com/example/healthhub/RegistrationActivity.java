package com.example.healthhub;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.internal.zzx;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstName,lastName, dob, address, unitNum, city, state,zipCode,email, password;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //To display back arrow that can take user back to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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


        //create a database instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //button action
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               patient = new Patient();
               patient.setFirstName(firstName.getText().toString());
               patient.setLastName(lastName.getText().toString());
               patient.setDateOfBirth(dob.getText().toString());
               patient.setUnitNumber(unitNum.getText().toString());
               patient.setCity(city.getText().toString());
               patient.setState(state.getText().toString());
               patient.setZipCode(zipCode.getText().toString());
               patient.setEmail(email.getText().toString());
               patient.setPassword(password.getText().toString());

               //Checking if password is valid
               //At least one capital,lower case, special character and one digit
               if(patient.validPassword()) {
                   //Insert java object into firebase database
                   db.collection("Patients").document().set(patient);
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