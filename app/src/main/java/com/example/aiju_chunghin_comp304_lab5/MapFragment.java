package com.example.aiju_chunghin_comp304_lab5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        double latitude = Double.parseDouble(pref.getString("LAT", ""));
        double longitude = Double.parseDouble(pref.getString("LNT", ""));

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                // on load
                LatLng selectedCity = new LatLng(latitude, longitude);
                map = googleMap;
//                map.addMarker(new MarkerOptions().position(selectedCity).title("marker in toronto"));

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        selectedCity, 12.0f), 3000, null);

                // on click
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        // When clicked on map
                        // Initialize marker options
                        MarkerOptions markerOptions=new MarkerOptions();

                        // Set position of marker
                        markerOptions.position(latLng);

                        // Set title of marker
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                        // Remove all marker
                        googleMap.clear();

                        // Animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                        // Add marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });

        return view;
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_3:
//                map.animateCamera(CameraUpdateFactory.zoomIn());
//                break;
//            case KeyEvent.KEYCODE_1:
//                map.animateCamera(CameraUpdateFactory.zoomOut());
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
