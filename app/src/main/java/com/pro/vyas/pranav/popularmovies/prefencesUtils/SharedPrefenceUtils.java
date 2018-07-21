package com.pro.vyas.pranav.popularmovies.prefencesUtils;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefenceUtils {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor editor;
    private static final String KEY_FIRST_RUN = "first_time_run";

    public SharedPrefenceUtils(SharedPreferences mPrefs) {
        this.mPrefs = mPrefs;
        editor = mPrefs.edit();
        editor.apply();
    }

    public boolean isFirstTimeRun() {
        return mPrefs.getInt(KEY_FIRST_RUN, 0) == 0;
    }

    public void setFirstTimeRun(boolean firstRun) {
        if (!firstRun) {
            editor.putInt(KEY_FIRST_RUN, 1);
            editor.apply();
        } else {
            editor.putInt(KEY_FIRST_RUN, 0);
            editor.apply();
        }
    }

    public String getData(String key) {
        return mPrefs.getString(key, "");
    }

    public void setData(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public List<String> getDataArray(String[] keys) {
        List<String> values = new ArrayList<>();
        for (String x : keys) {
            values.add(mPrefs.getString(x, ""));
        }
        return values;
    }

    public void setDataArray(String[] keys, String[] values) {
        for (int i = 0; i < keys.length; i++) {
            editor.putString(keys[i], values[i]);
        }
        editor.apply();
    }

}
