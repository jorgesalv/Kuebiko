package com.example.iot.internetofthings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Sensors extends AppCompatActivity {

    TextView water,food,door,shit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Change the name of the Screen in the phone
        getSupportActionBar().setTitle("Farm measures");
        setContentView(R.layout.activity_sensors);

        //Instantiate the fields
        water = findViewById(R.id.editText4);
        food = findViewById(R.id.editText);
        door = findViewById(R.id.editText2);
        shit = findViewById(R.id.editText3);
        //Show the actual values of the sensors from the database
        water.setText(MainActivity.dbSensors.getSensorValue(2));
        food.setText(MainActivity.dbSensors.getSensorValue(1));
        door.setText(MainActivity.dbSensors.getSensorValue(4));
        shit.setText(MainActivity.dbSensors.getSensorValue(3));


    }
    //Back button function
    @Override
    public void onBackPressed() {
        Intent profile = new Intent(Sensors.this, MainActivity.class);
        startActivity(profile);
        finish();
    }
}
