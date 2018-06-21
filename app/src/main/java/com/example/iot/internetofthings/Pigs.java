package com.example.iot.internetofthings;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.iot.internetofthings.MainActivity.db;

public class Pigs extends AppCompatActivity {


    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Change the name of the activity
        getSupportActionBar().setTitle("Pigs Record");
        setContentView(R.layout.activity_pigs);


        //Instantiate the list with the widget
        list = findViewById(R.id.listPigs);
        //Get the list of pigs from the Database in the MainActivity
        ArrayList<String> pigsinfo = MainActivity.db.getNames();
        //Create and adapter to be able to show the names in the list
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,pigsinfo);
        //Show the list of pigs in the farm
        list.setAdapter(adapter);


    }
    //Function to active the back button of the phone to go back to the Main Activity
    @Override
    public void onBackPressed() {
        Intent profile = new Intent(Pigs.this, MainActivity.class);
        startActivity(profile);
        finish();
    }
}
