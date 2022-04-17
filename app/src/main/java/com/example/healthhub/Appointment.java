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
    String itemSelected ;

    String docId;

    int docIndex;

    Object iSel;

    int appSelectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


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

                                String temp = document.get("AvailableTimes").toString().replace('[', ' ');
                                temp = temp.replace(']', ' ');
                                temp.trim();

                                AvailableTimes.add(temp);
                                listOfDocs.get(index).setAvailableTimes(AvailableTimes);
                                index++;
                            }
                        } 
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Appointment.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                        spinner.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }

                });

        //Select a data and time
        Spinner spinnerTwo = findViewById(R.id.spinner3);


        //First spinner action. Loads second spinner with corresponding available appointment times.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> listTwo = new ArrayList<>();
                int index = adapterView.getSelectedItemPosition();
                docIndex =index;



                for (int j = 0; j < listOfDocs.get(docIndex).getAvailableSize(); j++) {
                    listTwo.add(listOfDocs.get(docIndex).getAvailableTimes(j));
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


        //Spinner two functionality. Displays appointment summary data into textview
        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int index = adapterView.getSelectedItemPosition();
                appSelectedIndex = index;

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Appointment Summary: " + "\n" + "\n");
                stringBuilder.append("Doctor's Name: " + listOfDocs.get(docIndex).getName() + "\n" );
                stringBuilder.append("Specialty: " + listOfDocs.get(docIndex).getSpecialty() + "\n");
                stringBuilder.append("Address : " + listOfDocs.get(docIndex).getAddress() + "\n");
                stringBuilder.append("Suite: " + listOfDocs.get(docIndex).getSuite() + "\n");
                stringBuilder.append("City: " + listOfDocs.get(docIndex).getCity() + "\n");
                stringBuilder.append("State: " + listOfDocs.get(docIndex).getState() + "\n");
                stringBuilder.append("Zipcode : " + listOfDocs.get(docIndex).getZipCode() + "\n");
                stringBuilder.append("Telephone Number: " + listOfDocs.get(docIndex).getPhoneNumber() + "\n");
                stringBuilder.append("Appointment Time: " + adapterView.getSelectedItem().toString() + "\n");

                itemSelected = adapterView.getSelectedItem().toString();


                textView.setText(stringBuilder);

                Log.d(TAG, itemSelected);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //-----------------------------------------------------------------------------------------------

        //Button functionality. TODO working on a way to delete selected available time from the database
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String docName = listOfDocs.get(docIndex).getName();


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
                                         //iSel = document.getString(itemSelected);
                                    }

                                    Log.d(TAG, docId);

                                    //db.collection("Users").document(doc).collection("Doctors").document(docId)
                                            //.update("AvailableTimes", FieldValue.arrayRemove(itemSelected)).getResult();

                                }
                                else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }

                                String selected = itemSelected.trim();

                                //listOfDocs.get(docIndex).getAvailableTimes(appSelectedIndex).
                                DocumentReference ref = db.collection("Users").document(doc).collection("Doctors").document(docId);

                                //ref.update("AvailableTimes", FieldValue.arrayRemove(iSel) {

                                ref.update("AvailableTimes", FieldValue.arrayRemove(selected)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Log.d(TAG,"Successful deletion ");
                                            ref.get().toString();
                                        }
                                        else {
                                            Log.d(TAG, "Something went wrong");
                                        }
                                    }
                                });


                            }
                        });



                //DocumentReference document = db.collection("Users").document(doc).collection("Doctors").document(docId);
                //document.update("AvailableTimes", FieldValue.arrayRemove());
                //String id = document.getId();
                //Toast.makeText(Appointment.this, id, Toast.LENGTH_LONG).show();

                Toast.makeText(Appointment.this, "Appointment Confirmed", Toast.LENGTH_LONG).show();

                //db.terminate();

                Intent backToOptions = new Intent(Appointment.this, Options.class);
                startActivity(backToOptions);





            }//End of on-click



        });//End of button listener

    }
}