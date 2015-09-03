package com.uninorte.androidsensors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Luis on 19/04/2015.
 */
public class GPS extends Fragment implements LocationListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText mEditTextLat;
    private EditText mEditTextLong;
    private EditText mEditTextSpeed;
    private Button mButton;
    private LinearLayout mGroup;
    private RadioButton device;
    private boolean mStarted;
    private LocationManager mLocationManager;


    public static GPS newInstance(int sectionNumber) {
        GPS fragment = new GPS();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gps, container, false);

        mEditTextLat = (EditText) rootView.findViewById(R.id.editText4);
        mEditTextLong = (EditText) rootView.findViewById(R.id.editText5);
        mEditTextSpeed = (EditText) rootView.findViewById(R.id.editText6);
        mButton = (Button) rootView.findViewById(R.id.buttonGps);
        mEditTextLat.setText("");
        mEditTextLong.setText("");
        mGroup = (LinearLayout) rootView.findViewById(R.id.GPSSett);
        device = (RadioButton) rootView.findViewById(R.id.device_gps);
        mStarted = false;
        mButton.setText("Start Sensor");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mStarted) {
                    startCapture(getActivity());
                }else {
                    stopCapturing();
                }
            }
        });

        return rootView;
    }


    public void startCapture(Context context){
        mStarted = true;
        mButton.setText("Stop Sensor");
        mGroup.setVisibility(View.INVISIBLE);
        mLocationManager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE);
        boolean enabled;
        if (device.isChecked())
        {
            enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        else {
            enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        if (!enabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Set other dialog properties
            builder.setTitle("Location services are not enabled");
            builder.setMessage("Do you want us to take to Location Settings?");
            builder.setCancelable(false);
            // Add the buttons
            builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
            builder = builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            return;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (device.isChecked())
        {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, this);
        }
        else {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0, this);
        }
    }

    public void stopCapturing() {
        mStarted = false;
        mButton.setText("Start Sensor");
        mGroup.setVisibility(View.VISIBLE);
        mEditTextLat.setText("");
        mEditTextLong.setText("");
        mEditTextSpeed.setText("");
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }

    }

    private String ConvertToDegrees(Double angle)
    {
        double deg = Math.floor(angle);
        double minutes = Math.floor(Math.abs(deg) * 60) % 60;
        double seconds = (Math.abs(angle)*3600) % 60;
        return deg + "°" + minutes+ "'" + seconds + "''";
    }

    @Override
    public void onLocationChanged(Location location) {
        mEditTextLat.setText(ConvertToDegrees(location.getLatitude()));
        mEditTextLong.setText(ConvertToDegrees(location.getLongitude()));
        mEditTextSpeed.setText(((location.getSpeed()*3600)/1000)+"km/h");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
