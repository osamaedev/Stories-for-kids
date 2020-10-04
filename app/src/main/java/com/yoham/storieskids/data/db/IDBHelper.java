package com.yoham.storieskids.data.db;

import com.yoham.storieskids.data.db.model.Category;
import com.yoham.storieskids.data.db.model.Story;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IDBHelper {

    Single<Story> getStoryById(int id);

    Observable<List<Story>> getAllJohaStories();

    Observable<List<Story>> getAllGeneralStories();

    Observable<List<Story>> getAllAnbiaaStories();

    Observable<List<Story>> getAllAnimalsStories();

    Single<Boolean> setStoryAsFavourite(int storyId, int valueToSet);

    Observable<List<Story>> getAllFavouritesStories();

}
