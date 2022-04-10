package com.example.healthhub;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //THE FOLLOWING CODE CREATES CLICKABLE LINKS FOR RESET AND REGISTER ACTIONS.
        TextView textView = findViewById(R.id.textView4);
        TextView textView1 = findViewById(R.id.textView);

        //Mapping username and password edittext
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);

        //Mapping BUTTON ELEMENT
        button = findViewById(R.id.button);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();



        //Log in- Check username and password store in Firestore database to authenticate the user
        //If successful, user will be directed to the options page. If not a log in failure message
        //will display
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If username and password fields are not blank, allow login attempt
                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {

                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "sign in success");
                                    //Toast.makeText(MainActivity.this, "Successful login", Toast.LENGTH_LONG).show();
                                    Intent intent3 = new Intent(MainActivity.this, Options.class);

                                    startActivity(intent3);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "sign in failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                }
                //Otherwise display a message telling the user that one of these fields is blank
                else {
                    password.setError("email and password fields can not be blank");
                    password.requestFocus();
                }
            }
        });

        //REGISTRATION AND FORGOT
        String text = "New user? Register";
        String text2 = "Forgot Password?";

        SpannableString register = new SpannableString(text);
        SpannableString reset = new SpannableString(text2);

        //Clicking the register link will take the user to the registration page---------------
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
        };

        //Link that will take user to reset username or password activity
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                Intent intent2 = new Intent(MainActivity.this, resetUserOrPass.class);
                startActivity(intent2);
            }
        };

        //Making the Register link clickable
        register.setSpan(clickableSpan, 9 , 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        reset.setSpan(clickableSpan1,0,16,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(register);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView1.setText(reset);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
        //-------------------------------------------------------------------------------------

    }

}