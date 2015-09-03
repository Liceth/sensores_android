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

/**
 * Created by Luis on 19/04/2015.
 */
public class Light extends Fragment implements SensorEventListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private EditText mEditTextX;
    private Button mButton;
    private boolean mStarted;


    public static Light newInstance(int sectionNumber) {
        Light fragment = new Light();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.light, container, false);
        mEditTextX = (EditText) rootView.findViewById(R.id.editText13);
        mButton = (Button) rootView.findViewById(R.id.buttonLight);
        mEditTextX.setText("");
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
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopCapturing() {
        mStarted = false;
        mEditTextX.setText("");
        mButton.setText("Start Sensor");
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mLightSensor);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCapturing();
        mEditTextX.setText("");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mEditTextX.setText(event.values[0] + " mLux");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
