package com.example.aiju_chunghin_comp304_lab5;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        double[] LATLNT = intent.getDoubleArrayExtra("LATLNT");

//        Toast.makeText(this.getApplicationContext(), LATLNT[0] + ", " + LATLNT[1], Toast.LENGTH_SHORT).show();

        // Initialize fragment
        Fragment fragment = new MapFragment();
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_layout, fragment)
                .commit();

//        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        if (permission == PERMISSION_GRANTED) {
//            // TODO Access the location-based services.
//
//            Fragment fragment = new Fragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
////            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
////            mapFragment.getMapAsync(this);
//        } else {
//            // Request fine location permission.
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                // TODO Display additional rationale for the requested permission.
//
//            }
//
//            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 0
//                    );
//        }
    }
}