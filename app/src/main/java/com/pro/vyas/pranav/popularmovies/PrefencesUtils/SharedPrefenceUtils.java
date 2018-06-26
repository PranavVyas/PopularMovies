package com.pro.vyas.pranav.popularmovies.PrefencesUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefenceUtils {

    Context context;
    SharedPreferences mPrefs;
    SharedPreferences.Editor editor;
    private static final String KEY_FIRST_RUN = "first_time_run";

    public SharedPrefenceUtils(Context context,SharedPreferences mPrefs) {
        this.context = context;
        this.mPrefs = mPrefs;
        editor = mPrefs.edit();
    }

    public boolean isFirstTimeRun(){
        if (mPrefs.getInt(KEY_FIRST_RUN, 0) == 0) return true;
        else return false;
    }

    public void setFirstTimeRun(boolean firstRun){
        if(firstRun == false){
            editor.putInt(KEY_FIRST_RUN, 1);
            editor.commit();
        }else {
            editor.putInt(KEY_FIRST_RUN, 0);
            editor.commit();
        }
    }

    //
    // SharedPreferences prefs =
    //public boolean firsttimerun(){
//
 //   }
}
