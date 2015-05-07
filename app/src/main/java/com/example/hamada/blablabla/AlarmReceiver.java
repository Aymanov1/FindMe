package com.example.hamada.blablabla;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {


    private static final double LATITUDE_ENIS = 34.7262714;
    private static final double LONGITUDE_ENIS = 10.71665168420000002;
    private static final double METER = 0.000008998719243599958;
    GPSTracker gps;
    GlobalVariables a;
    String Favourite_Number ;


// hedhi class elli biha na3mel execution mta3 tache repetitive

    @Override
    public void onReceive(Context context, Intent intent) {
        Favourite_Number= intent.getStringExtra("num");
        gps =new GPSTracker(context);

        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();




            if (gps.canGetLocation()) {
                // int lost = cercle(LATITUDE_ENIS, LONGITUDE_ENIS, 50);
                //hedhi fonction elli ta3mel zone vert
                // si yo5roj mnha  tab3eth message tgollah rani dhaya3


                SmsManager sm = SmsManager.getDefault();
                String message = "Unfortunately I am LOST \n" +
                        "Your Location is -\n" + "Lat: " + latitude + "\nLong: \n" +
                        longitude;
                sm.sendTextMessage(Favourite_Number, null, message, null, null);
                Toast.makeText(context, "message send , you re out of range " + Favourite_Number, Toast.LENGTH_SHORT).show();



           // Toast.makeText(context, Favourite_Number, Toast.LENGTH_LONG).show();


        }
    }}





