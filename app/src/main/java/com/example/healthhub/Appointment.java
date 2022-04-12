package com.example.healthhub;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        //To display back arrow that can take user back to MainActivity
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String doc = auth.getCurrentUser().getUid();


        //List to store doctor's names
        ArrayList<String> list = new ArrayList<>();



        //Array of Doctor objects to hold all doctors
        ArrayList<Doctor> listOfDocs = new ArrayList<Doctor>();

        List<String> AvailableTimes = new ArrayList<>();

        Spinner spinner = findViewById(R.id.spinner);

        //THIS CODE GETS DOCTOR'S NAME AND SPECIALTY FROM DATABASE OF DOCTORS INTO A SPINNER
        db.collection("Users").document(doc).collection("Doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int index = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                list.add((document.get("name").toString()) + "-" + document.get("specialty").toString());
                                listOfDocs.add(document.toObject(Doctor.class));
                                AvailableTimes.add(Objects.requireNonNull(document.get("AvailableTimes")).toString());
                                listOfDocs.get(index).setAvailableTimes(AvailableTimes);
                                index++;


                            }

                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Appointment.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                        spinner.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }

                });

        //Select a data and time
        Spinner spinnerTwo = findViewById(R.id.spinner3);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected = adapterView.getSelectedItem().toString();

                Toast.makeText(Appointment.this,itemSelected,Toast.LENGTH_LONG).show();

                ArrayList<String> listTwo = new ArrayList<>();
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Appointment.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listTwo);
                spinnerTwo.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();

                int index = adapterView.getSelectedItemPosition();
                //listTwo.add(listOfDocs.get(index).
                for(int j = 0; j < listOfDocs.get(index).getAvailableSize(); j++) {
                    listTwo.add(listOfDocs.get(index).getAvailableTimes(j));
                }

                //adapter2.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Appointment.this, "Nothing selected", Toast.LENGTH_LONG).show();

            }
        });

    }
}