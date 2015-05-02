package com.example.hamada.blablabla;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Passage extends PreferenceActivity {


    private static final double LATITUDE_ENIS = 34.7262714;
    private static final double LONGITUDE_ENIS = 10.71665168420000002;
    private static final double METER = 0.000008998719243599958;
    double LatitudePlace ;
    double LongitudePlace ;
    String Favourite_Number ;
    int Diameter_Zone ;
    int Time_sending ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage);

        addPreferencesFromResource(R.xml.pref_zone);
        addPreferencesFromResource(R.xml.pref_data_sync);
        addPreferencesFromResource(R.xml.pref_general);
        addPreferencesFromResource(R.xml.pref_headers);

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            LatitudePlace = Double.parseDouble(prefs.getString(getString(R.string.pref_Latitude), "34.7262714"));
            LongitudePlace = Double.parseDouble(prefs.getString(getString(R.string.pref_Longitude), "10.71665168420000002"));
            Favourite_Number = prefs.getString(getString(R.string.pref_Favourite_Number), "23664801");
            Diameter_Zone = Integer.parseInt(prefs.getString(getString(R.string.pref_Diameter_Zone), "50"));
            Time_sending = Integer.parseInt(prefs.getString(getString(R.string.pref_Time_sending), "60"));
        }
        catch(NullPointerException ex){
            ex.printStackTrace();
            LatitudePlace = 34.7262714;
            LongitudePlace = 10.71665168420000002;
            Favourite_Number =  "23664801";
            Diameter_Zone =50;
            Time_sending = 60;

        }

    }

         void envoi() {
        GPSTracker gps;
        //get and send location information
        gps = new GPSTracker(getApplicationContext());
        if (gps.canGetLocation()) {
            // int lost = cercle(LATITUDE_ENIS, LONGITUDE_ENIS, 50);
            //hedhi fonction elli ta3mel zone vert
            // si yo5roj mnha  tab3eth message tgollah rani dhaya3
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();


            double rayonConverti = Diameter_Zone * METER;
          // en cas ou tel barra zone verte
                SmsManager sm = SmsManager.getDefault();
                String message = "Unfortunately I am LOST \n" +
                        "Your Location is -\n" + "Lat: " + latitude + "\nLong: \n" +
                        longitude;
                sm.sendTextMessage(Favourite_Number, null, message, null, null);
                Toast.makeText(getApplicationContext(), "message send , you re out of range ", Toast.LENGTH_SHORT).show();

        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_passage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}