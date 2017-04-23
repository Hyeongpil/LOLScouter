package com.hyeongpil.lolscouter.util;

/**
 * Created by hyeongpil on 2017-01-14.
 */

import android.app.Activity;
import android.content.Context;

public class SharedPreferences {
    final static String TAG = "SharedPreferences";
    private final String mPreferenceId = "com.hyeongpil.overscouter"; // 대충 아무거나 적어도됨
    private android.content.SharedPreferences pref;
    private android.content.SharedPreferences.Editor editor;

    private Context mContext;

    public SharedPreferences(Context c) {
        mContext = c;
        pref = mContext.getSharedPreferences(mPreferenceId, Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * string 저장
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public String getString(String key) {
        try {
            return getString(key, "");
        } catch (Exception e) {}

        return "";
    }

    public void removeString(String key){
        editor.remove(key);
        editor.commit();
    }

    public String getString(String key, String val) {
        try {
            return pref.getString(key, val);
        } catch (Exception e) {
            return val;
        }
    }

    public int getInt(String key, int val){
        try {
            return pref.getInt(key, val);
        } catch (Exception e) {
            return val;
        }
    }
    public int getInt(String key){
        try {
            return pref.getInt(key, 1);
        } catch (Exception e) {
            return 1;
        }
    }
}
