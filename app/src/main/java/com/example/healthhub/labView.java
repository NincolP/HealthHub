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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class labView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_view);
        Bundle bundle = getIntent().getExtras();

        String url = bundle.getString("url");

        String useID = bundle.getString("Userid");
        String documentiD = bundle.getString("documentId");

        Log.d(TAG, url);


        Button mButton = findViewById(R.id.button11);

        WebView webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //auth.updateCurrentUser(useID);
        //String doc = auth.getCurrentUser().getUid();

        //StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);

        //StorageReference doc = ref.child("bloodwork.jpg");

       /* doc.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

            }


        });*/





        //DocumentReference ref = db.collection("Users").document(useID)
                //.collection("Lab Reports").document(documentiD);




        mButton.setOnClickListener((View v) -> {
            Intent backToReportsActivity = new Intent(labView.this, ReportsActivity.class);
            startActivity(backToReportsActivity);
        });

        /*com.google.firebase.storage.StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("labReports/bloodWork.jpg");
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
        }*/
    }
}