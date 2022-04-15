package com.example.healthhub;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_report);

        //Instantiating database instance and capturing database userid
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String doc = auth.getCurrentUser().getUid();

        Button button = findViewById(R.id.button9);

        Spinner type = findViewById(R.id.spinner4);

        ArrayList<String> types = new ArrayList<>();


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
                  for (QueryDocumentSnapshot document : task.getResult()) {
                      Log.d(TAG, document.getId() + " => " + document.getData());
                      types.add(document.get("type").toString());
                  }
              }

              else {

              }

                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(ReportsActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, types);
                type.setAdapter(typeAdapter);
                typeAdapter.notifyDataSetChanged();

            }
        });


    }


}



