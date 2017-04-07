package com.example.annie.myfirstapp;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayCompassActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView mPointer;
    private TextView textDegree;
    private ConstraintLayout layout;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;

    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];

    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;

    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];

    private float mCurrentDegree = 0f;
    private Vibrator vibrator;
    private int oldGroup;
    private int currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_compass);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mPointer = (ImageView) findViewById(R.id.imageArrow);
        textDegree = (TextView) findViewById(R.id.textDegree);
        layout = (ConstraintLayout) findViewById(R.id.compassLayout);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
      /*  if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometer = event.values;
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometer = event.values;
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];

            azimuthInRadiansSmooth = filter(azimuthInRadians, azimuthInRadiansSmooth);

            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
            RotateAnimation ra = new RotateAnimation(
                    mCurrentDegree,
                    -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            ra.setDuration(250);
            ra.setFillAfter(true);
*/
        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float) (Math.toDegrees(azimuthInRadians) + 360) % 360;
            RotateAnimation ra = new RotateAnimation(
                    mCurrentDegree,
                    -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            ra.setDuration(250);

            ra.setFillAfter(true);

            mPointer.startAnimation(ra);
            mCurrentDegree = -azimuthInDegress;

            mPointer.startAnimation(ra);
            mCurrentDegree = -azimuthInDegress;
            doColors(azimuthInDegress);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void doColors(float azimuthInDegress) {
        String degreeText = Float.toString((Math.abs((int) azimuthInDegress - 359)));
        int nbrChars = degreeText.length();
        String degree = degreeText.substring(0, nbrChars - 2);
        textDegree.setText(degree + "°");

        int color = getColorInteger((Math.abs((int) azimuthInDegress - 359)));
        layout.setBackgroundColor(color);
    }

    public int getColorInteger(int degree) {
        int resultColor = 0;
        if (degree < 30) {
            currentGroup = 1;
            resultColor = Color.parseColor("#e6f2ff");
        } else if (degree < 60) {
            currentGroup = 2;
            resultColor = Color.parseColor("#f2e6ff");
        } else if (degree < 90) {
            currentGroup = 3;
            resultColor = Color.parseColor("#ffe6ff");
        } else if (degree < 120) {
            currentGroup = 4;
            resultColor = Color.parseColor("#ffe6f2");
        } else if (degree < 150) {
            currentGroup = 5;
            resultColor = Color.parseColor("#ffe6e6");
        } else if (degree < 180) {
            currentGroup = 6;
            resultColor = Color.parseColor("#fff2e6");
        } else if (degree < 210) {
            currentGroup = 7;
            resultColor = Color.parseColor("#ffffe6");
        } else if (degree < 240) {
            currentGroup = 8;
            resultColor = Color.parseColor("#f2ffe6");
        } else if (degree < 270) {
            currentGroup = 9;
            resultColor = Color.parseColor("#e6ffe6");
        } else if (degree < 300) {
            currentGroup = 10;
            resultColor = Color.parseColor("#e6fff2");
        } else if (degree < 330) {
            currentGroup = 11;
            resultColor = Color.parseColor("#e6fff2");
        } else {
            currentGroup = 12;
            resultColor = Color.parseColor("#e6f7ff");
        }
        if (currentGroup == 1 || currentGroup == 12) {
            if (currentGroup != oldGroup){
                vibrator.vibrate(50);
                vibrator.cancel();
            }
        }
        oldGroup = currentGroup;
        return resultColor;
    }
}