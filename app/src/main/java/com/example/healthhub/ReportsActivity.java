package com.example.healthhub;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    String childImage;

    int documentIndex;

    String docID;

    String userID;

    StorageReference stoRef;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_report);

        //Instantiating database instance and capturing database userid
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();


        String doc = auth.getCurrentUser().getUid();

        userID = doc;

        Button button = findViewById(R.id.button9);

        Spinner type = findViewById(R.id.spinner4);

        TextView message = findViewById(R.id.textView6);

        TextView summary = findViewById(R.id.textView7);



        ArrayList<String> types = new ArrayList<>();

        ArrayList<Report> reports = new ArrayList<>();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToOptions = new Intent(ReportsActivity.this, Options.class);
                startActivity(backToOptions);

            }
        });

        db.collection("Users").document(doc).collection("Lab Reports")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
              if(task.isSuccessful()) {

                  int index = 0;
                  for (QueryDocumentSnapshot document : task.getResult()) {

                      Log.d(TAG, document.getId() + " => " + document.getData());
                      types.add(document.get("type").toString());

                      //THis should load lab reports information into an array of lab reports
                      reports.add(document.toObject(Report.class));
                      reports.get(index).setReportId(document.getId());

                      Log.d(TAG, document.getId());
                      index++;
                  }
              }

              else {

              }

                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(ReportsActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, types);
                type.setAdapter(typeAdapter);
                typeAdapter.notifyDataSetChanged();

            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();

                documentIndex = index;

                Log.d(TAG, reports.get(index).getReport());

                childImage = reports.get(index).getReport();

                docID = reports.get(index).getReportId();

                //stoRef = FirebaseStorage.getInstance().getReference().child(childImage);

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("Lab Report Summary: " + "\n" + "\n");
                stringBuilder.append("Provider's name: " + reports.get(documentIndex).getProviderName() + "\n" );
                stringBuilder.append("Report Type: " + reports.get(documentIndex).getType()+ "\n" );

                stringBuilder.append("Date: " + reports.get(documentIndex).getDate()+ "\n" );
                stringBuilder.append("Provider's Address: " + reports.get(documentIndex).getProviderAddress() + "\n" );
                stringBuilder.append("Provider's Unit Number: " + reports.get(documentIndex).getUnitNumber() + "\n" );
                stringBuilder.append("Provider's City: " + reports.get(documentIndex).getCity() + "\n" );
                stringBuilder.append("Provider's State: " + reports.get(documentIndex).getState() + "\n" );
                stringBuilder.append("Provider's Zipcode: " + reports.get(documentIndex).getZipCode() + "\n" );
                stringBuilder.append("Provider's Phone Number: " + reports.get(documentIndex).getPhoneNumber() + "\n" );

                summary.setText(stringBuilder);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        message.setText("Click on next to see report");


        Button button1 = findViewById(R.id.button10);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ReportsActivity.this, labView.class);
                //Create the bundle
                Bundle bundle = new Bundle();
                bundle.putString("url", childImage);
                bundle.putString("documentId", docID);
                bundle.putString("Userid", userID);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }


}




