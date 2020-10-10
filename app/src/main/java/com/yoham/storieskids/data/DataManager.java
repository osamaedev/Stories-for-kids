package com.yoham.storieskids.data;

import android.content.Context;

import com.yoham.storieskids.data.db.DBHelper;
import com.yoham.storieskids.data.db.model.Category;
import com.yoham.storieskids.data.db.model.Story;
import com.yoham.storieskids.data.prefs.PreferencesHelper;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DataManager implements IDataManager {


    private DBHelper mDBHelper;
    private Context mContext;
    private PreferencesHelper mPreferenceHelper;


    public DataManager(Context context, DBHelper dbHelper) {
        this.mContext = context;
        this.mDBHelper = dbHelper;
        this.mPreferenceHelper = new PreferencesHelper(mContext);
    }

    @Override
    public Flowable<Story> getStoryById(int id) {
        return DBHelper.getInstance(mContext).getStoryById(id);
    }


    @Override
    public Flowable<List<Story>> getAllJohaStories() {
        return DBHelper.getInstance(mContext).getAllJohaStories();
    }

    @Override
    public Observable<List<Story>> getAllGeneralStories() {
        return DBHelper.getInstance(mContext).getAllGeneralStories();
    }

    @Override
    public Observable<List<Story>> getAllAnbiaaStories() {
        return DBHelper.getInstance(mContext).getAllAnbiaaStories();
    }

    @Override
    public Observable<List<Story>> getAllAnimalsStories() {
        return DBHelper.getInstance(mContext).getAllAnimalsStories();
    }

    @Override
    public Observable<List<Story>> getAllFavouritesStories() {
        return DBHelper.getInstance(mContext).getAllFavouritesStories();
    }

    @Override
    public Single<Boolean> setStoryAsFavourite(int storyId, int valueToSet) {
        return DBHelper.getInstance(mContext).setStoryAsFavourite(storyId, valueToSet);
    }

    @Override
    public void setIsRated(boolean value) {
        mPreferenceHelper.setIsRated(value);
    }

    @Override
    public void setTimeUsingApp(Long value) {
        mPreferenceHelper.setTimeUsingApp(value);
    }

    @Override
    public Long getTimeUsingApp() {
        return mPreferenceHelper.getTimeUsingApp();
    }

    @Override
    public boolean getIsRated() {
        return mPreferenceHelper.getIsRated();
    }

    @Override
    public int getNumberOfLaunches() {
        return mPreferenceHelper.getNumberOfLaunches();
    }

    @Override
    public void setNumberOfLaunches(int value) {
        mPreferenceHelper.setNumberOfLaunches(value);
    }
}
