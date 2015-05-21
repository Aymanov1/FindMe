package com.example.hamada.blablabla;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.gsm.SmsManager;


public class AlarmReceiver extends BroadcastReceiver {


    private static final double LATITUDE_ENIS = 34.7262714;
    private static final double LONGITUDE_ENIS = 10.71665168420000002;
    private static final double METER = 0.000008998719243599958;
    GPSTracker gps;

    String Favourite_Number;


// hedhi class elli biha na3mel execution mta3 tache repetitive

    @Override
    public void onReceive(Context context, Intent intent) {


        Favourite_Number = "12345678";
        gps = new GPSTracker(context);

        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        if (gps.canGetLocation()) {


            SmsManager sm = SmsManager.getDefault();
            String message = "Unfortunately I am LOST \n" +
                    "Your Location is -\n" + "Lat: " + latitude + "\nLong: \n" +
                    longitude;
            sm.sendTextMessage(Favourite_Number, null, message, null, null);


            // Toast.makeText(context, Favourite_Number, Toast.LENGTH_LONG).show();


        }
    }
}





