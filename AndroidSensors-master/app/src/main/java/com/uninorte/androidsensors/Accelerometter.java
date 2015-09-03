package com.uninorte.androidsensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Luis on 19/04/2015.
 */
public class Accelerometter extends Fragment implements SensorEventListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private SensorManager mSensorManager;
    private Sensor mAcelSensor;
    private EditText mEditTextX;
    private EditText mEditTextY;
    private EditText mEditTextZ;
    private LinearLayout ly;
    private RadioButton normal_act;
    private Button mButton;
    private boolean mStarted;


    public static Accelerometter newInstance(int sectionNumber) {
        Accelerometter fragment = new Accelerometter();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.accelerometer, container, false);
        normal_act = (RadioButton) rootView.findViewById(R.id.normal_acc_sett);
        mEditTextX = (EditText) rootView.findViewById(R.id.editText);
        mEditTextY = (EditText) rootView.findViewById(R.id.editText1);
        mEditTextZ = (EditText) rootView.findViewById(R.id.editText2);
        mButton = (Button) rootView.findViewById(R.id.buttonAccel);
        ly = (LinearLayout) rootView.findViewById(R.id.AccelerometerSett);
        mEditTextX.setText("");
        mEditTextY.setText("");
        mEditTextZ.setText("");
        mStarted = false;
        mButton.setText("Start Sensor");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mStarted) {
                    startCapture(getActivity());
                } else {
                    stopCapturing();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        stopCapturing();
        mEditTextX.setText("");
        mEditTextY.setText("");
        mEditTextZ.setText("");
    }

    @Override
    public void onStop() {
        super.onStop();
        stopCapturing();
    }

    public void startCapture(Context context) {
        mStarted = true;
        mButton.setText("Stop Sensor");
        ly.setVisibility(View.INVISIBLE);
        mSensorManager = (SensorManager) getActivity().getSystemService(context.SENSOR_SERVICE);
        mAcelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (normal_act.isChecked()) {
            mSensorManager.registerListener(this, mAcelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            mSensorManager.registerListener(this, mAcelSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

    }

    public void stopCapturing() {
        mStarted = false;
        mEditTextX.setText("");
        mEditTextY.setText("");
        mEditTextZ.setText("");
        ly.setVisibility(View.VISIBLE);
        mButton.setText("Start Sensor");
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mAcelSensor);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCapturing();
        mEditTextX.setText("");
        mEditTextY.setText("");
        mEditTextZ.setText("");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mEditTextX.setText(event.values[0] + "g");
        mEditTextY.setText(event.values[1] + "g");
        mEditTextZ.setText(event.values[2] + "g");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
