package com.example.healthhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //THE FOLLOWING CODE CREATES CLICKABLE LINKS FOR RESET AND REGISTER ACTIONS.
        TextView textView = findViewById(R.id.textView4);
        TextView textView1 = findViewById(R.id.textView);

        String text = "New user? Register";
        String text2 = "Forgot Username/Password?";

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
        reset.setSpan(clickableSpan1,0,25,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(register);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView1.setText(reset);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
        //-------------------------------------------------------------------------------------



        //BUTTON ELEMENT
        Button button = findViewById(R.id.button);

        //TEXT FIELDS FOR USERNAME AND PASSWORD
        EditText userName = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);


        //TO DO need to authenticate first before allowing the next code
        //which switches the user to home page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(userName.getText().equals(TODO DATABase username) && password.getText().equals(TO DO db password)) {
                Intent intent3 = new Intent(MainActivity.this, Options.class);
                startActivity(intent3);
               // }
            }
        });
    }


}