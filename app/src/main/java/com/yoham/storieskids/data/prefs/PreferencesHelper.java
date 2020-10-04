package com.yoham.storieskids.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.yoham.storieskids.utils.AppConstants;

public class PreferencesHelper implements IPreferencesHelper {

    private static final String PREF_DATE_FIRST_LAUNCH = "DATE_FIRST_LAUNCH";
    private static final String PREF_DON_T_SHOW_AGAIN_RATE = "DON_T_SHOW_RATE_US";
    private static final String PREF_NUMBER_OF_LAUNCHES = "NUMBER_OF_LAUNCH";


    private final SharedPreferences mPrefs;

    public PreferencesHelper(Context context) {
        mPrefs = context.getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Long getTimeUsingApp() {
        return mPrefs.getLong(PREF_DATE_FIRST_LAUNCH, AppConstants.NULL_INDEX);
    }

    @Override
    public boolean getIsRated() {
        return mPrefs.getBoolean(PREF_DON_T_SHOW_AGAIN_RATE, false);
    }

    @Override
    public void setIsRated(boolean value) {
        mPrefs.edit().putBoolean(PREF_DON_T_SHOW_AGAIN_RATE, value).apply();
    }

    @Override
    public void setTimeUsingApp(Long value) {
        mPrefs.edit().putLong(PREF_DATE_FIRST_LAUNCH, value).apply();
    }

    @Override
    public int getNumberOfLaunches() {
        return mPrefs.getInt(PREF_NUMBER_OF_LAUNCHES, 0);
    }

    @Override
    public void setNumberOfLaunches(int value) {
        mPrefs.edit().putInt(PREF_NUMBER_OF_LAUNCHES, value).apply();
    }
}
