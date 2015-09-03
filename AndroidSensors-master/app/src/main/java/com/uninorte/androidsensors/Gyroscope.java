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
public class Gyroscope extends Fragment implements SensorEventListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private SensorManager mSensorManager;
    private Sensor mGyroSensor;
    private EditText mEditTextX;
    private EditText mEditTextY;
    private EditText mEditTextZ;
    private Button mButton;
    private boolean mStarted;


    public static Gyroscope newInstance(int sectionNumber) {
        Gyroscope fragment = new Gyroscope();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gyroscope, container, false);
        mEditTextX = (EditText) rootView.findViewById(R.id.editText7);
        mEditTextY = (EditText) rootView.findViewById(R.id.editText8);
        mEditTextZ = (EditText) rootView.findViewById(R.id.editText9);
        mButton = (Button) rootView.findViewById(R.id.buttonGyro);
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
        mSensorManager = (SensorManager) getActivity().getSystemService(context.SENSOR_SERVICE);
        mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mGyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopCapturing() {
        mStarted = false;
        mEditTextX.setText("");
        mEditTextY.setText("");
        mEditTextZ.setText("");
        mButton.setText("Start Sensor");
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mGyroSensor);
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
        mEditTextX.setText(event.values[0] + "rads/sec");
        mEditTextY.setText(event.values[1] + "rads/sec");
        mEditTextZ.setText(event.values[2] + "rads/sec");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
