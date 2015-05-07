package com.example.hamada.blablabla;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.app.ActionBar;

import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class FindMe extends ActionBarActivity implements View.OnClickListener{
SendService mService;
    GlobalVariables a;
    GPSTracker gps;
    double LatitudePlace;
    double LongitudePlace;
    String Favourite_Number;
    int Diameter_Zone;
    int Time_sending;

    boolean mBound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);


        gps = new GPSTracker(getApplicationContext());



        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            LatitudePlace=Double.parseDouble(prefs.getString(getString(R.string.pref_Latitude), "34.7262714"));
            LongitudePlace=Double.parseDouble(prefs.getString(getString(R.string.pref_Longitude), "10.71665168420000002"));
            Favourite_Number= prefs.getString(getString(R.string.pref_Favourite_Number), "23664801");
            Diameter_Zone=Integer.parseInt(prefs.getString(getString(R.string.pref_Diameter_Zone), "50"));
            Time_sending=Integer.parseInt(prefs.getString(getString(R.string.pref_Time_sending), "60"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {

            LatitudePlace = 34.7262714;
            LongitudePlace = 10.71665168420000002;
            Favourite_Number = "23664801";
            Diameter_Zone = 50;
            Time_sending = 60;
        }



    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        LatitudePlace = Double.parseDouble(prefs.getString(getString(R.string.pref_Latitude), "34.7262714"));
        LongitudePlace = Double.parseDouble(prefs.getString(getString(R.string.pref_Longitude), "10.71665168420000002"));
        Favourite_Number = prefs.getString(getString(R.string.pref_Favourite_Number), "23664801");
        Diameter_Zone = Integer.parseInt(prefs.getString(getString(R.string.pref_Diameter_Zone), "50"));
        Time_sending = Integer.parseInt(prefs.getString(getString(R.string.pref_Time_sending), "60"));


        Intent i =new Intent(this,SendService.class);
        bindService(i,mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBound){

            unbindService(mConnection);
            mBound=false;
        }
    }
    private ServiceConnection mConnection =new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SendService.LocalBinder binder =(SendService.LocalBinder) service;
            mService = binder.getService();
            mBound= true;
            mService.getSituation();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {mBound=false;

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_binding, menu);
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
            item.setVisible(true);

            startActivity(new Intent (getApplicationContext(),Settings.class));
            return true;
        }
        if (id == R.id.action_refresh) {
            item.setVisible(true);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            LatitudePlace = Double.parseDouble(prefs.getString(getString(R.string.pref_Latitude), "34.7262714"));
            LongitudePlace = Double.parseDouble(prefs.getString(getString(R.string.pref_Longitude), "10.71665168420000002"));
            Favourite_Number = prefs.getString(getString(R.string.pref_Favourite_Number), "23664801");
            Diameter_Zone = Integer.parseInt(prefs.getString(getString(R.string.pref_Diameter_Zone), "50"));
            Time_sending = Integer.parseInt(prefs.getString(getString(R.string.pref_Time_sending), "60"));
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
