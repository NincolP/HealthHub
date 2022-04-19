package com.example.healthhub;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    String url;

    int documentIndex;

    String docID;

    String userID;

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







        ArrayList<String> types = new ArrayList<>();

        ArrayList<Report> reports = new ArrayList<>();

        //Reports report = new Reports();


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



                Log.d(TAG, reports.get(index).getReport().getPath());

                url = reports.get(index).getReport().getPath();

                docID = reports.get(index).getReportId();






            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        message.setText("Click on next to see report");

        //textView6 = (TextView) findViewById(R.id.textView6);
                //textView6.setText(TextView.AUT);

        Button button1 = findViewById(R.id.button10);
       // button1.setOnClickListener(v -> openNEXT());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this, labView.class);

                //Create the bundle
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                bundle.putString("documentId", docID);
                bundle.putString("Userid", userID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    //public void openNEXT () {
       // Intent intent = new Intent(ReportsActivity.this, labView.class);
       // startActivity(intent);

    }

//}



