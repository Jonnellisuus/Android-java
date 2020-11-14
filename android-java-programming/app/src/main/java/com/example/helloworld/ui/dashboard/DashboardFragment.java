package com.example.helloworld.ui.dashboard;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworld.InternetReceiver;
import com.example.helloworld.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment implements LocationListener {

    private DashboardViewModel dashboardViewModel;

    TextInputEditText locationLatitude, locationLongitude, locationAddress;
    Button showLocationOnMap;

    double latitude, longitude;

    LocationManager locationManager;
    Location lastLocation;

    Geocoder geocoder;
    List<Address> addressList;

    private BroadcastReceiver internetReceiver = null;

    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 111;
    private static final String TAG = "DashboardFragment";
    String currentLocation = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        internetReceiver = new InternetReceiver();

        locationLatitude = root.findViewById(R.id.locationLatitude);
        locationLatitude.setEnabled(false);

        locationLongitude = root.findViewById(R.id.locationLongitude);
        locationLongitude.setEnabled(false);

        locationAddress = root.findViewById(R.id.locationAddress);
        locationAddress.setEnabled(false);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        startLocationUpdate();
        broadcastIntent();

        showLocationOnMap = root.findViewById(R.id.showLocationButton);
        showLocationOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastLocation != null) {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", latitude, longitude);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        startLocationUpdate();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.requireActivity().unregisterReceiver(internetReceiver);
    }

    public void broadcastIntent() {
        this.requireActivity().registerReceiver(internetReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_FINE_LOCATION);

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (lastLocation != null) {
            locationLatitude.setText(Double.toString(lastLocation.getLatitude()));
            locationLongitude.setText(Double.toString(lastLocation.getLongitude()));
            locationAddress.setText(getAddress(lastLocation));
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permission, int[] grantResults) {
        Log.e(TAG, "permsRequestCode = " + permsRequestCode);
        switch (permsRequestCode) {
            case MY_PERMISSION_REQUEST_FINE_LOCATION:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lastLocation = location;
        locationLatitude.setText(Double.toString(lastLocation.getLatitude()));
        locationLongitude.setText(Double.toString(lastLocation.getLongitude()));
        locationAddress.setText(getAddress(lastLocation));
    }

    public String getAddress(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                addressList = geocoder.getFromLocation(latitude, longitude, 1);
                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);

                    currentLocation = address.getAddressLine(0);

                    Log.e(TAG, address.getAddressLine(0));
                }
            } catch (IOException e) {
                Log.e(TAG, "Unable to connect Geocoder", e);
            }
        }
        return currentLocation;
    }
}