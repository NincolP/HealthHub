package com.example.healthhub;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class labView extends AppCompatActivity {

    StorageReference stoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_view);
        Bundle bundle = getIntent().getExtras();

        String url = bundle.getString("url");
        String user = bundle.getString("Userid");
        String docId = bundle.getString("documentId");

        Log.d(TAG, url);

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        Button mButton = findViewById(R.id.button11);


        stoRef = FirebaseStorage.getInstance().getReference().child(url);

        try {
            final File localFile = File.createTempFile("tempFile", "jpg");
            stoRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(labView.this, "local file is fine", Toast.LENGTH_LONG).show();

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Something went wrong");
        }


        //Back Button
        mButton.setOnClickListener((View v) -> {
            Intent backToReportsActivity = new Intent(labView.this, ReportsActivity.class);
            startActivity(backToReportsActivity);
        });

    }
}