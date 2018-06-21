package com.example.iot.internetofthings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.iot.internetofthings.MainActivity.db;

public class AddPigs extends AppCompatActivity {


    EditText origin,lot,race,depdate,arrivdate;
    Button addB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pigs);

        //Instantiate the different fields of the form
        origin = findViewById(R.id.editText9);
        lot = findViewById(R.id.editText11);
        race = findViewById(R.id.editText12);
        depdate = findViewById(R.id.editText13);
        arrivdate = findViewById(R.id.editText9);
        //Instantiate the button
        addB = findViewById(R.id.addButton);
        //Define the action to do on the click
        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the insert is good we are going back to the Main Activity
                if(db.insertPigKeyboard(origin.getText().toString(),arrivdate.getText().toString(),depdate.getText().toString(),race.getText().toString(),lot.getText().toString())){
                    Intent homeIntent = new Intent(AddPigs.this, MainActivity.class);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(homeIntent);
                    finish();
                }
            }
        });

    //Back button function
    }
    @Override
    public void onBackPressed() {
        Intent profile = new Intent(AddPigs.this, MainActivity.class);
        startActivity(profile);
        finish();
    }
}
