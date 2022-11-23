package com.example.aiju_chunghin_comp304_lab5;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Initialize fragment
        Fragment fragment = new MapFragment();
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_layout, fragment).setReorderingAllowed(true)
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