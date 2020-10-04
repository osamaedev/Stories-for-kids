package com.yoham.storieskids.data.prefs;

public interface IPreferencesHelper {

    void setIsRated(boolean value);

    void setTimeUsingApp(Long value);

    Long getTimeUsingApp();

    boolean getIsRated();

    int getNumberOfLaunches();

    void setNumberOfLaunches(int value);

}
