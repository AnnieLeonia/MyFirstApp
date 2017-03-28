package com.example.annie.myfirstapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class DisplaySensorActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sensor);


        TextView textView5 = (TextView) findViewById(R.id.textView5);
        textView5.setText("X-value: " + 1);

        TextView textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText("Y-value: " + 2);

        TextView textView7 = (TextView) findViewById(R.id.textView7);
        textView7.setText("Z-value: " + 3);


    }
}


