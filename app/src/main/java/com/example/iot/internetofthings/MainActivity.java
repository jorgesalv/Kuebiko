package com.example.iot.internetofthings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends AppCompatActivity {
    private Button button,water,food,shit,door;
    private EditText time;
    private TextView finalResult,sensorText;
    public static BaseHelper db,dbSensors;
    Button pigs,sensors;
    FloatingActionButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar name
        getSupportActionBar().setTitle("Kuebiko");
        setContentView(R.layout.activity_main);
        //Create the pigs table
        db = new BaseHelper(this);
        //create the sensors table;
        dbSensors = new BaseHelper(this);

        //Insert some values in the pigs table
        if (db.numberOfRows()==0){
            db.insertPig("000000000000000000000001","Peppa","Cat","2018-05-05","2018-05-05","54","iber");
            db.insertPig("000000000000000000000002","Chorizo","Cat","2018-05-05","2018-05-05","54","iber");
            db.insertPig("000000000000000000000003","Caramel","Cat","2018-05-05","2018-05-05","54","iber");
            db.insertPig("000000000000000000000004","Frankfurter","Cat","2018-05-05","2018-05-05","54","iber");
            db.insertPig("000000000000000000000005","Holly","Cat","2018-05-05","2018-05-05","54","iber");
            db.insertPig("000000000000000000000006","Jimbo","Cat","2018-05-05","2018-05-05","54","iber");
            db.insertPig("000000000000000000000007","Mr. Pig","Cat","2018-05-05","2018-05-05","54","iber");
            db.insertPig("000000000000000000000008","Mrs. Pig","Cat","2018-05-05","2018-05-05","54","iber");
            db.insertPig("000000000000000000000009","Nugget","Cat","2018-05-05","2018-05-05","54","iber");
            db.insertPig("000000000000000000000010","Popeye","Cat","2018-05-05","2018-05-05","54","iber");
        }
        //Insert some value in the sensors table
        if(dbSensors.numberOfSensorRows()==0){
            dbSensors.insertSensor(1,"food","90","000000000000000000000001");
            dbSensors.insertSensor(2,"water","76","000000000000000000000001");
            dbSensors.insertSensor(3,"shit","25","000000000000000000000001");
            dbSensors.insertSensor(4,"door","ON","000000000000000000000001");
        }
        //button to read an RFID tag
        button = (Button) findViewById(R.id.btn_run);
        //TextView of the final result, where the information of the RFID tag will appear
        finalResult = (TextView) findViewById(R.id.textView2);
        //Do the async task when clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String myUrl = "http://google.com";
                //Instantiate new instance of our class
                HttpGetRequest getRequest = new HttpGetRequest();
                //Perform the doInBackground method, passing in our url
                getRequest.execute(myUrl);
            }


        });

        //Button to go to another activity and see the pigs record
        pigs = findViewById(R.id.pig);
        //Button to go to another activity and see the sensors actual information
        sensors = findViewById(R.id.sensors);
        //Button to manually add a pig
        add = findViewById(R.id.floatingActionButton);
        //OnClickListeners
        pigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MainActivity.this, Pigs.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(homeIntent);
                finish();
            }
        });
        sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MainActivity.this, Sensors.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(homeIntent);
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MainActivity.this, AddPigs.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(homeIntent);
                finish();
            }
        });

        //Buttons to refill the different sensors
        water = findViewById(R.id.button);
        food = findViewById(R.id.button2);
        shit = findViewById(R.id.button3);
        door = findViewById(R.id.button4);
    }


    //AsyncTask
    public class HttpGetRequest extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params){
            publishProgress("Connecting...");
            String result="";
            finalResult.setText("Pigs Information\n");
            try {
                //Connect to the RFID Emulator and see for the devices
                //And get the ids
                URL url = new URL("http://10.0.2.2:3161/devices");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                String id = doc.getElementsByTagName("id").item(0).getTextContent();
                url = new URL("http://10.0.2.2:3161/devices/" + id +"/start");
                doc = db.parse(new InputSource(url.openStream()));
                url = new URL("http://10.0.2.2:3161/devices/" + id +"/inventory");
                doc = db.parse(new InputSource(url.openStream()));
                int length = doc.getElementsByTagName("epc").getLength();
                StringBuilder sb = new StringBuilder();
                for(int i = 0;i<length;i++){
                    //For the number of ids obteined get the infromation from the database
                    //Separate the pigs and the sensors with a ","
                    sb.append(cerdoHere(Integer.parseInt(doc.getElementsByTagName("epc").item(i).getTextContent()) ));
                    sb.append(",");
                    sb.append(cerdoHereSensor(Integer.parseInt(doc.getElementsByTagName("epc").item(i).getTextContent()) ));
                }
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                result = e.getMessage();
            }

            return result;
        }
        //Dismiss the progress dialog
        protected void onPostExecute(String result){
            progressDialog.dismiss();
            finalResult.append(result);
            progressDialog.dismiss();
        }
        //Show the progress dialog
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Connecting to Emulator");

        }

    }
    //Function to get the pigs from the id, of the database
    public String cerdoHere(int id){
        Cursor c =db.getData(id);
        String name="";
        Random rand = new Random();
        int random_integer = rand.nextInt(300-200) + 200;
        if(c.moveToNext()){
          name = c.getString(1) + " was here\n" + "And is weighting "+ random_integer + "Kg.\n";
        }
         return  name;
    }
    //Function to get the sensors id form the pigs id read
    public String cerdoHereSensor(int id){
        ArrayList<String> rows = db.getSensorInfo(id);
        String name="";
        System.out.println("*********"+rows);
        for (String s : rows){
            name += s + "\n";
            System.out.println("*********"+name);
        }
        return  name;
    }



}