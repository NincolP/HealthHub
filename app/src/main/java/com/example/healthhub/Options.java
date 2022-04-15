package com.example.healthhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);

        Button makeAppointment = findViewById(R.id.button3);

        Button rx = findViewById(R.id.button4);

        Button labReport = findViewById(R.id.button5);

        Button logout = findViewById(R.id.logoutButton);

        //Take the user to the make appointment activity
        makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent makeAppointment = new Intent(Options.this, Appointment.class);
                startActivity(makeAppointment);
            }
        });

        //Take the user to the prescriptions activity
        rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rx = new Intent(Options.this, FillRx.class);
                startActivity(rx);
            }
        });

        //Take the user to the lab report activity
        labReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lab = new Intent(Options.this, ReportsActivity.class);
                startActivity(lab);
            }
        });

        //Log out will take user to main screen
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOut = new Intent(Options.this, MainActivity.class);

                startActivity(logOut);
            }
        });

    }

}