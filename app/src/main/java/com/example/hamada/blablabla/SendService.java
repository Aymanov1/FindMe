package com.example.hamada.blablabla;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SendService extends Service {

    GPSTracker gps;
    private static final double LATITUDE_ENIS = 34.7262714;
    private static final double LONGITUDE_ENIS = 10.71665168420000002;
    private static final double METER = 0.000008998719243599958;
    IBinder mBinder=new LocalBinder();
   /* private int Time_sending=1;
    private double LatitudePlace=34.7262714;
    private double LongitudePlace=10.71665168420000002;
    private int Diameter_Zone=50;*/

    double LatitudePlace ;
    double LongitudePlace ;
    String Favourite_Number ;
    int Diameter_Zone ;
    int Time_sending ;

    SharedPreferences prefs ;



    public SendService(int time_sending, double latitudePlace, double longitudePlace, String favourite_Number, int diameter_Zone,SharedPreferences prefs) {


        try {
           prefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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

    public SendService() {

    }

    protected void getSituation(){



        gps = new GPSTracker(SendService.this);

        if (gps.canGetLocation()) {
            int lost = cercle(LatitudePlace, LongitudePlace, Diameter_Zone);
            //hedhi fonction elli ta3mel zone vert
            // si yo5roj mnha  tab3eth message tgollah rani dhaya3
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            if (lost == 1) {

                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Time_sending*1000,
                        pendingIntent);// repitition kol  1/2 minute
            }
            else
                Toast.makeText(getApplicationContext(), "fortunately I am not LOST !!!!! ", Toast.LENGTH_SHORT).show();


            //alarm service hedhi class elli fiha repitition
            // mta3 procedure mo3ayna kol par  exemple minute== 1800000 millisecondes



        } else {
            // affichez alert annou mafamech bch nhez location
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
                Toast.makeText(getApplicationContext(), "You are LOST !!!!! ", Toast.LENGTH_SHORT).show();


                return 1;// ma3naha dhaye3
            }

        }
        return 0;// mahouch dhaye3
    }

    @Override
    public IBinder onBind(Intent intent) {

return mBinder;




    }

    public class LocalBinder extends Binder {


        SendService getService()
        {


            return SendService.this;


        }
    }
}
