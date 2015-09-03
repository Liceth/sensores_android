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

/**
 * Created by Luis on 19/04/2015.
 */
public class Compass extends Fragment implements SensorEventListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private SensorManager mSensorManager;
    private Sensor mCompSensor;
    private EditText mEditTextX;
    private EditText mEditTextY;
    private EditText mEditTextZ;
    private LinearLayout ly;
    private RadioButton normal_act;
    private Button mButton;
    private boolean mStarted;


    public static Compass newInstance(int sectionNumber) {
        Compass fragment = new Compass();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.compass, container, false);
        normal_act = (RadioButton) rootView.findViewById(R.id.normal_wait);
        mEditTextX = (EditText) rootView.findViewById(R.id.editText10);
        mEditTextY = (EditText) rootView.findViewById(R.id.editText11);
        mEditTextZ = (EditText) rootView.findViewById(R.id.editText12);
        mButton = (Button) rootView.findViewById(R.id.buttonCompass);
        ly = (LinearLayout) rootView.findViewById(R.id.CompSett);
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
        mCompSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (normal_act.isChecked()) {
            mSensorManager.registerListener(this, mCompSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            mSensorManager.registerListener(this, mCompSensor, SensorManager.SENSOR_DELAY_FASTEST);
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
            mSensorManager.unregisterListener(this, mCompSensor);
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
        mEditTextX.setText(event.values[0] + "uT");
        mEditTextY.setText(event.values[1] + "uT");
        mEditTextZ.setText(event.values[2] + "uT");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
