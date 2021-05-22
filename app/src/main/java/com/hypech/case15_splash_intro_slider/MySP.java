package com.hypech.case15_splash_intro_slider;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class MySP {

    public static SharedPreferences sp = null;
    public SharedPreferences.Editor editor = null;
    public static MySP mySP;

    private MySP() {    }
    public static MySP getInstance() {
        if (mySP == null) {
            synchronized (MySP.class) {
                if (mySP == null) {
                    // double synchronize lock
                    mySP = new MySP();
                }
            }
        }
        return mySP;
    }

    public void init(Context context) {
        // application life-cycle
        sp = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        if (editor == null) editor = sp.edit();
    }

    public void  saveString(String key, String value) {editor.putString(key, value).commit();}
    public void     saveInt(String key, int value)    {editor.putInt(key, value).commit();   }
    public void saveBoolean(String key, boolean value){editor.putBoolean(key, value).commit();}

    public String  getString(String key) {return sp.getString(key, "");    }
    public int     getInt(String key)    {return sp.getInt(key, 0);}
    public boolean getBoolean(String key){return sp.getBoolean(key, false);    }

    public synchronized void remove(String key) {
        if (editor == null) editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }
}