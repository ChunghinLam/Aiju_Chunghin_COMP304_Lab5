package com.example.aiju_chunghin_comp304_lab5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MapFragment extends Fragment {
    private GoogleMap map;
    private int mapType = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        double latitude = Double.parseDouble(pref.getString("LAT", ""));
        double longitude = Double.parseDouble(pref.getString("LNT", ""));

        // get cinemas
        List<Cinemas> cinemas = getList("cinemas", pref);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapLoad(googleMap, latitude, longitude, cinemas);
            }
        });

        Switch swSatelliteView = view.findViewById(R.id.swSatelliteView);
        swSatelliteView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mapType = isChecked ? GoogleMap.MAP_TYPE_HYBRID : GoogleMap.MAP_TYPE_NORMAL;

                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        // on load
                        mapLoad(googleMap, latitude, longitude, cinemas);
                    }
                });
            }
        });

        return view;
    }

    private void mapLoad(GoogleMap googleMap, double latitude, double longitude, List<Cinemas> cinemas) {
        LatLng selectedCity = new LatLng(latitude, longitude);
        map = googleMap;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                selectedCity, 12.0f), 3000, null);
        map.setMapType(mapType);

        // add marker
        for (Cinemas c : cinemas) {
            LatLng latLng = new LatLng(c.getLat(), c.getLnt());
            map.addMarker(new MarkerOptions().position(latLng).title(c.getCinemaName()));
        }

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

    public List<Cinemas> getList(String key, SharedPreferences pref) {
        List<Cinemas> arrayItems = null;
        String serializedObject = pref.getString(key, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Cinemas>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }

        return arrayItems;
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
