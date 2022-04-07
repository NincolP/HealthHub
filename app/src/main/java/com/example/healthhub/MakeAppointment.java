package com.example.healthhub;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MakeAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String doc = auth.getCurrentUser().getUid();


        //Spinner to select doctor
        Spinner selectDoctor = findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList<String>();

        //Spinner to select appointment date and time
        Spinner selectAppointment = findViewById(R.id.selectAppointment);
        ArrayList<String> list2 = new ArrayList<String>();


        //Array of Doctor objects tio hold all doctors
        ArrayList<Doctor> listOfDocs = new ArrayList<Doctor>();


        //THIS CODE GETS DOCTOR'S NAME AND SPECIALTY FROM DATABASE OF DOCTORS INTO A SPINNER
        db.collection("Users").document(doc).collection("Doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                list.add(Objects.requireNonNull(document.get("name")) + "-" + document.get("specialty"));
                                listOfDocs.add(document.toObject(Doctor.class));

                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
        selectDoctor.setAdapter(adapter);
        //-------------------------------------------------------------------------------------------------------------------------------------

        //CREATE AN EVENT WHEN A NAME IS SELECTED.
        selectDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int index = adapterView.getSelectedItemPosition();
                for(int j = 0; j < listOfDocs.get(index).getAvailableSize(); j++) {
                    list2.add(listOfDocs.get(index).getAvailableTimes(j));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list2);
        selectAppointment.setAdapter(adapterTwo);

        selectAppointment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String selected = adapterView.getSelectedItem().toString();

                //Toast.makeText(MakeAppointment.this, selected, Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
}