package com.example.healthhub;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstName,lastName, dob, address, unitNum, city, state,zipCode,email, password;
    Patient patient;
    DatabaseReference ref;

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

        ref = FirebaseDatabase.getInstance().getReference().child("Patients");

        //Instantiate a patient object
        patient = new Patient();

        //Register button
        Button register = findViewById(R.id.button2);

        //FirebaseFirestore db = FirebaseFirestore.getInstance();



        //button action
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               patient.setFirstName(firstName.getText().toString());
               patient.setLastName(lastName.getText().toString());
               patient.setDateOfBirth(dob.getText().toString());
               patient.setUnitNumber(unitNum.getText().toString());
               patient.setCity(city.getText().toString());
               patient.setState(state.getText().toString());
               patient.setZipCode(zipCode.getText().toString());
               //patient.setEmail(email.getText().toString());
               //patient.setPassword(password.getText().toString());

               ref.push().setValue(patient);

               //db.collection("Patients").document("newUser").set(patient);

           }
       });




        //Saving username and password as shown in class TODO decide username and password storage
        //The other option is to place this data in database
        /*SharedPreferences preferences=getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("username", userName.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.commit();*/

    }




    //This will take the user back to main page, in case the remember they are already registered
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}