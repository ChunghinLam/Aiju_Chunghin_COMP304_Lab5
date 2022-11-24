package com.example.aiju_chunghin_comp304_lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // coordinates of Scarborough Oakville Hamilton Brampton
    private final double[] LATLNT_SCARBO = new double[]{ 43.777702, -79.233238 , 0 };
    private final double[] LATLNT_OAK = new double[]{ 43.4669, -79.6858 , 1 };
    private final double[] LATLNT_HAMIL = new double[]{ 43.255203, -79.843826, 2 };
    private final double[] LATLNT_BRAMP = new double[]{ 43.683334, -79.766670, 3 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<Integer, double[]> mapLATLNT = new HashMap<>();
        mapLATLNT.put(0, LATLNT_SCARBO);
        mapLATLNT.put(1, LATLNT_OAK);
        mapLATLNT.put(2, LATLNT_HAMIL);
        mapLATLNT.put(3, LATLNT_BRAMP);

        SharedPreferences pref = getSharedPreferences("Pref", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();

        // cinemas handling
        List<Cinemas> cinemasList = generateCinemas();
        setList("cinemas", cinemasList, prefEditor);

        // movie list handling
        ListView lvLocationList = findViewById(R.id.lvLocationList);
        String[] locations = getResources().getStringArray(R.array.locationList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, locations);

        lvLocationList.setAdapter(adapter);
        lvLocationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.putExtra("LATLNT", mapLATLNT.get(position));

                prefEditor.putString("LAT", mapLATLNT.get(position)[0] + "");
                prefEditor.putString("LNT", mapLATLNT.get(position)[1] + "");
                prefEditor.putString("KEY", mapLATLNT.get(position)[2] + "");
                prefEditor.commit();

                startActivity(intent);
            }
        });
    }

    private List<Cinemas> generateCinemas() {
        List<Cinemas> list = new ArrayList<>();

        // Scarborough
        list.add(
            new Cinemas("Cineplex Cinemas Scarborough", 43.77902484707892, -79.25522211279386,
                "+14162905217", "09:00 - 23:00", "https://www.cineplex.com/Theatre/cineplex-cinemas-scarborough")
        );
        list.add(
            new Cinemas("Cineplex Odeon Morningside Cinemas", 43.80128366566005, -79.20313041060764,
                "+14162811444", "09:00 - 23:00", "https://www.cineplex.com/Theatre/cineplex-odeon-morningside-cinemas")
        );
        list.add(
            new Cinemas("Cineplex Odeon Eglinton Town Centre Cinemas", 43.72665840985135, -79.26734997131088,
                "+14167524494", "09:00 - 23:00", "http://www.cineplex.com/Theatre/cineplex-odeon-eglinton-town-centre-cinemas")
        );
        // Oakville
        list.add(
          new Cinemas("Cineplex Cinemas Oakville and VIP", 43.395678745035454, -79.75164813477491,
                  "+19058277173", "09:45 –21:15", "https://www.cineplex.com/Theatres/VIP")
        );
        list.add(
                new Cinemas("Cineplex Cinemas Winston Churchill & VIP", 43.51505580429028, -79.65895099026253,
                        "+19058292009", "Mon–Thu 3:45 pm–10:00 pm Fri 3:45 pm–10:30 pm Sat, Sun 1:00 pm–10:30 pm", "http://www.cineplex.com/vip")
        );
        // Hamilton
        list.add(
                new Cinemas("Cineplex Cinemas Hamilton Mountain", 43.19352678464059, -79.80919360160459,
                        "+19055600239", "17:45–22:45", "https://www.cineplex.com/theatre/cineplex-cinemas-hamilton-mountain")
        );
        // Brampton
        list.add(
                new Cinemas("SilverCity Brampton Cinemas", 43.73655811372682, -79.76686027778374,
                        "+19057896797", "09:15–23:00", "https://www.cineplex.com/theatre/silvercity-brampton-cinemas")
        );
        list.add(
                new Cinemas("Cineplex Odeon Orion Gate Cinemas", 43.68394637150859, -79.71604850962471,
                        "+19054551590", "12:00–23:00", "https://www.cineplex.com/theatre/cinema-cineplex-odeon-carrefour-dorion")
        );
        list.add(
                new Cinemas("Cineplex Cinemas Courtney Park", 43.64097898505509, -79.69098594830302,
                        "+19053621234", "11:30–23:00", "https://www.cineplex.com/theatre/cineplex-cinemas-courtney-park")
        );
        return list;
    }

    public <T> void setList(String key, List<T> list, SharedPreferences.Editor prefEditor) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(key, json, prefEditor);
    }

    public void set(String key, String value, SharedPreferences.Editor prefEditor) {
        prefEditor.putString(key, value);
        prefEditor.commit();
    }
}