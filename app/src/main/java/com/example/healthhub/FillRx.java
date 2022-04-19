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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FillRx extends AppCompatActivity {

    int refillsAvailable;
    int newNumberOfRefills;
    String rxID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_rx);


        Button backButton = findViewById(R.id.button7);

        Button refill = findViewById(R.id.button11);

        refill.setEnabled(false);

        TextView rxSummary = findViewById(R.id.textView6);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToOptions = new Intent(FillRx.this, Options.class);
                startActivity(backToOptions);

            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String doc = auth.getCurrentUser().getUid();


        List<String> list = new ArrayList<>();

        ArrayList<Prescription> prescriptions= new ArrayList<>();

        ArrayList<String> rxIds = new ArrayList<>();

        Spinner spinner = findViewById(R.id.spinner2);

        db.collection("Users").document(doc).collection("Prescription")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int index = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                list.add((document.get("name").toString()));
                                prescriptions.add(document.toObject(Prescription.class));
                                prescriptions.get(index).setRxID(document.getId());

                                Log.d(TAG,document.getId());
                                index++;

                            }

                        }
                        else {
                            Log.d(TAG, "Error getting documents", task.getException());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                        spinner.setAdapter(adapter);
                    }

                });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected = adapterView.getSelectedItem().toString();
                //Toast.makeText(FillRx.this, itemSelected, Toast.LENGTH_LONG).show();
                int index = adapterView.getSelectedItemPosition();

                int rxIndex = index;

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Prescription Name: " + prescriptions.get(index).getName() + "\n" );
                stringBuilder.append("Dose: " + prescriptions.get(index).getDose() + "\n" );
                stringBuilder.append("Quantity: " + prescriptions.get(index).getQuantity() + "\n" );
                stringBuilder.append("Production Date: " + prescriptions.get(index).getProductionDate() + "\n" );
                stringBuilder.append("Expiration Date: " + prescriptions.get(index).getExpirationDate() + "\n" );

                String refillAsString = String.valueOf(prescriptions.get(index).getRefills());
                stringBuilder.append("Refills Available: " + (refillAsString) + "\n" );

                stringBuilder.append("Upon successfully refill,your pharmacy will provide pick up details");
                rxSummary.setText(stringBuilder);


                refillsAvailable = prescriptions.get(index).getRefills();
                newNumberOfRefills = refillsAvailable -1;
                rxID = prescriptions.get(index).getRxID();


                if(refillsAvailable > 0) {
                    refill.setEnabled(true);

                }
                else {
                   refill.setEnabled(false);
                    Toast.makeText(FillRx.this, "THIS RX DOES NOT HAVE ANY REFILLS", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        refill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This code updates the refill count and display a message if successfully refill.
                int newCount = newNumberOfRefills;

                //Document reference
                DocumentReference rxRefillRef = db.collection("Users")
                        .document(doc).collection("Prescription").document(rxID);

                rxRefillRef.update("refills", newCount);
                Toast.makeText(FillRx.this, "Refill submitted to your pharmacy", Toast.LENGTH_LONG).show();
                Intent options = new Intent(FillRx.this, Options.class);
                startActivity(options);
            }
        });


    }






}