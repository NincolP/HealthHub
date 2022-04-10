package com.example.healthhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String doc = auth.getCurrentUser().getUid();


        //Spinner to select doctor

        List<String> list = new ArrayList<>();

        //Array of Doctor objects to hold all doctors
        ArrayList<Doctor> listOfDocs = new ArrayList<Doctor>();

        List<String> AvailableTimes = new ArrayList<>();
        //THIS CODE GETS DOCTOR'S NAME AND SPECIALTY FROM DATABASE OF DOCTORS INTO A SPINNER
        db.collection("Users").document(doc).collection("Doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int index = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());

                                list.add((document.get("name").toString()) + "-" + document.get("specialty").toString());
                                listOfDocs.add(document.toObject(Doctor.class));
                                AvailableTimes.add(Objects.requireNonNull(document.get("AvailableTimes")).toString());
                                listOfDocs.get(index).setAvailableTimes(AvailableTimes);

                                index++;

                                //Log.d(TAG, document.get("AvailableTimes").toString());

                                //Log.d(TAG, AvailableTimes.get(index));

                                //Log.d(TAG, listOfDocs.get(0).getSpecialty());
                            }

                        }
                        else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        /*Button button = findViewById(R.id.button6);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemSelected = spinner.getSelectedItem().toString();
                Toast.makeText(Appointment.this, itemSelected, Toast.LENGTH_LONG).show();
            }
        });*/

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Appointment.this, "item selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Appointment.this, "Nothing selected", Toast.LENGTH_LONG).show();

            }
        });

    }
}