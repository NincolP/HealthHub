package com.example.healthhub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;


public class labView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_view);


        Button mButton = findViewById(R.id.button11);


        mButton.setOnClickListener((View v) -> {
            Intent backToReportsActivity = new Intent(labView.this, ReportsActivity.class);
            startActivity(backToReportsActivity);
        });

        com.google.firebase.storage.StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("labReports/bloodWork.jpg");
        try {
            final File localFile = File.createTempFile("bloodWork", "jpg");
            mStorageReference.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(labView.this, "LabReports Retrieved", Toast.LENGTH_SHORT).show();
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
                    }).addOnFailureListener(ioException -> Toast.makeText(labView.this, "Error Occurred", Toast.LENGTH_SHORT).show());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}