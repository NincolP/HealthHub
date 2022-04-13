package com.example.healthhub;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Appointment extends AppCompatActivity {
    String selectedTime ;

    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        final int[] doctorIndex = new int[1];
        final int[] appointmentTimeIndex = new int[1];


        //Back button can take user back to options menu
        Button back = findViewById(R.id.button8);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Appointment.this, Options.class);
                startActivity(back);

            }
        });




        //Instantiating database instance and capturing database userid
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String doc = auth.getCurrentUser().getUid();


        //List to store doctor's names
        ArrayList<String> list = new ArrayList<>();


        //Array of Doctor objects to hold all doctors
        ArrayList<Doctor> listOfDocs = new ArrayList<Doctor>();

        List<String> AvailableTimes = new ArrayList<>();

        Spinner spinner = findViewById(R.id.spinner);

        TextView textView = findViewById(R.id.textView5);

        Button button = findViewById(R.id.button6);

        //THIS CODE GETS DOCTOR'S NAME AND SPECIALTY FROM DATABASE OF DOCTORS INTO A SPINNER
        //It also gets other doctors data from the database for each doctor and loads it in am
        //array of doctors objects.

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
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Appointment.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
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
                //Toast.makeText(Appointment.this,itemSelected,Toast.LENGTH_LONG).show();

                ArrayList<String> listTwo = new ArrayList<>();


                int index = adapterView.getSelectedItemPosition();
                doctorIndex[0] = index;

                for (int j = 0; j < listOfDocs.get(index).getAvailableSize(); j++) {
                    listTwo.add(listOfDocs.get(index).getAvailableTimes(j));
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Appointment.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listTwo);
                spinnerTwo.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Appointment.this, "Nothing selected", Toast.LENGTH_LONG).show();

            }
        });


        //Spinner two functionality----------------------------------------------------------------------
        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                //appointmentTimeIndex[0] = index;

                selectedTime = adapterView.getSelectedItem().toString();



                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Appointment Summary: " + "\n" + "\n");
                stringBuilder.append("Doctor's Name: " + listOfDocs.get(doctorIndex[0]).getName() + "\n" );
                stringBuilder.append("Specialty: " + listOfDocs.get(doctorIndex[0]).getSpecialty() + "\n");
                stringBuilder.append("Address : " + listOfDocs.get(doctorIndex[0]).getAddress() + "\n");
                stringBuilder.append("Suite: " + listOfDocs.get(doctorIndex[0]).getSuite() + "\n");
                stringBuilder.append("City: " + listOfDocs.get(doctorIndex[0]).getCity() + "\n");
                stringBuilder.append("State: " + listOfDocs.get(doctorIndex[0]).getState() + "\n");
                stringBuilder.append("Zipcode : " + listOfDocs.get(doctorIndex[0]).getZipCode() + "\n");
                stringBuilder.append("Telephone Number: " + listOfDocs.get(doctorIndex[0]).getPhoneNumber() + "\n");
                stringBuilder.append("Appointment Time: " + adapterView.getSelectedItem().toString() + "\n");
                textView.setText(stringBuilder);


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //-----------------------------------------------------------------------------------------------

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String docName = listOfDocs.get(doctorIndex[0]).getName();

                DocumentReference docRef;

                db.collection("Users").document(doc).collection("Doctors")
                        .whereEqualTo("name", docName)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        docId = document.getId();

                                    }

                                   /* db.collection("Users").document(doc).collection("Doctors").document(docId)
                                            .update("AvailableTimes", FieldValue.arrayRemove(selectedTime));
*/

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                DocumentReference document = db.collection("Users").document(doc).collection("Doctors").document(docId);
                //document.update("AvailableTimes", FieldValue.arrayRemove());
                //String id = document.getId();
                //Toast.makeText(Appointment.this, id, Toast.LENGTH_LONG).show();




                        //document.update("AvailableTimes", FieldValue.arrayRemove(selectedTime));

               /* db.collection("Users").document(doc).collection("Doctors").document(docId)
                        .update("AvailableTimes",FieldValue.arrayRemove(selectedTime)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Appointment.this, "Success", Toast.LENGTH_LONG).show();
                        }

                        else {
                            Log.d(TAG, "error", task.getException());
                        }
                    }
                });*/


            }//End of on-click



        });//End of button listener

    }
}