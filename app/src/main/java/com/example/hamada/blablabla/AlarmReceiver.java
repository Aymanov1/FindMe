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


public class AlarmReceiver extends BroadcastReceiver
    {


        private static final double LATITUDE_ENIS = 34.7262714;
        private static final double LONGITUDE_ENIS = 10.71665168420000002;
        private static final double METER = 0.000008998719243599958;
        Passage p =new Passage();



// hedhi class elli biha na3mel execution mta3 tache repetitive

        @Override
        public void onReceive(Context context, Intent intent)
        { p.envoi();
        }
        }



