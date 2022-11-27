package com.example.aiju_chunghin_comp304_lab5;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

        // get pin indexes
        List<Integer> index = new ArrayList<>();
        switch (Double.valueOf(pref.getString("KEY", null)).intValue()) {
            case 0:
                index.add(0);
                index.add(1);
                index.add(2);
                break;
            case 1:
                index.add(3);
                index.add(4);
                break;
            case 2:
                index.add(5);
                break;
            case 3:
                index.add(6);
                index.add(7);
                index.add(8);
                break;

            default:
                break;
        }


        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapLoad(googleMap, latitude, longitude, cinemas, index);
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
                        mapLoad(googleMap, latitude, longitude, cinemas, index);
                    }
                });
            }
        });

        return view;
    }

    private void mapLoad(GoogleMap googleMap, double latitude, double longitude,
                         List<Cinemas> cinemas, List<Integer> pinIndex) {
        LatLng selectedCity = new LatLng(latitude, longitude);
        map = googleMap;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                selectedCity, 12.0f), 3000, null);
        map.setMapType(mapType);

        // add marker
        for (Integer i : pinIndex){
            Cinemas c = cinemas.get(i);
            LatLng latLng = new LatLng(c.getLat(), c.getLnt());
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(c.getCinemaName())
                    .snippet("Open hours:\n"+c.getHours()+"\n"+
                             "Phone:"+c.getPhone())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.custom_marker))
            );
        }

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null;
            }

            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                Context mContext = getActivity();
                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);
                SharedPreferences pref = getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                ImageView badge = new ImageView(mContext);
                // Select badge image accordingly to the marker
                int badgeImg = R.drawable.def_img;
                List<Cinemas> cinemas = getList("cinemas", pref);
                if (marker.getTitle().equals(cinemas.get(6).getCinemaName()))
                    badgeImg = R.drawable.silvercity_brampton;
                else if (marker.getTitle().equals(cinemas.get(7).getCinemaName()))
                    badgeImg = R.drawable.odeon_orion_gate;
                badge.setImageResource(badgeImg);

                info.addView(badge);
                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

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
