package com.pro.vyas.pranav.popularmovies.PrefencesUtils;

import android.content.SharedPreferences;

public class SharedPrefenceUtils {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor editor;
    private static final String KEY_FIRST_RUN = "first_time_run";

    public SharedPrefenceUtils(SharedPreferences mPrefs) {
        this.mPrefs = mPrefs;
        editor = mPrefs.edit();
        editor.apply();
    }

    public boolean isFirstTimeRun(){
        return mPrefs.getInt(KEY_FIRST_RUN, 0) == 0;
    }

    public void setFirstTimeRun(boolean firstRun){
        if(!firstRun){
            editor.putInt(KEY_FIRST_RUN, 1);
            editor.apply();
        }else {
            editor.putInt(KEY_FIRST_RUN, 0);
            editor.apply();
        }
    }

    //
    // SharedPreferences prefs =
    //public boolean firsttimerun(){
//
 //   }
}
