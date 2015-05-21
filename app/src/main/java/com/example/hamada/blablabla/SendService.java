package com.example.hamada.blablabla;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;

public class SendService extends Service {
    private static final double LATITUDE_ENIS = 34.7262714;
    private static final double LONGITUDE_ENIS = 10.71665168420000002;
    private static final double METER = 0.000008998719243599958;
    IBinder mBinder = new LocalBinder();

    GPSTracker gps;

    double LatitudePlace = LATITUDE_ENIS;
    double LongitudePlace = LONGITUDE_ENIS;
    String Favourite_Number = "12345678";
    int Diameter_Zone = 50;
    int Time_sending = 5;


    protected void getSituation() {

        gps = new GPSTracker(SendService.this);

        if (gps.canGetLocation()) {
            int lost = cercle(LatitudePlace, LongitudePlace, Diameter_Zone);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            if (lost == 1) {


                Intent intent = new Intent(this, AlarmReceiver.class);
                SmsManager sm = SmsManager.getDefault();
                String message = "Unfortunately I am LOST \n" +
                        "Your Location is -\n" + "Lat: " + latitude + "\nLong: \n" +
                        longitude;
                sm.sendTextMessage(Favourite_Number, null, message, null, null);


                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Time_sending * 60000, pendingIntent);// repitition kol  1/2 minute
            } else {
            }


        } else {

            gps.showSettingsAlert();
        }


    }


    int cercle(double lat, double lon, int rayon) {
        double rayonConverti = rayon * METER;
        gps = new GPSTracker(SendService.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            if (Math.sqrt(Math.pow(lat - latitude, 2) + Math.pow(lon - longitude, 2)) > rayonConverti) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;


    }

    public class LocalBinder extends Binder {


        SendService getService() {


            return SendService.this;


        }
    }
}
