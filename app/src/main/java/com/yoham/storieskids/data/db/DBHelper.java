package com.yoham.storieskids.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.yoham.storieskids.data.db.model.Story;
import com.yoham.storieskids.utils.AppConstants;
import com.yoham.storieskids.utils.AppUtils;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static com.yoham.storieskids.utils.SecurityUtils.getSP;
import static com.yoham.storieskids.utils.SecurityUtils.decryptString;
import static com.yoham.storieskids.utils.SecurityUtils.getFP;

public class DBHelper extends SQLiteOpenHelper implements IDBHelper {

    private static final String TAG = "DBHelper";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private static final String STORY_TABLE = "Story";
    private static final String IS_FAVORITE_COLUMN= "is_favorite";
    private static final String ID_STORY= "id_story";



    private static DBHelper mInstance;

    public DBHelper(Context context) {
        super(context, AppConstants.DB_NAME,null, 1);
        this.mContext = context;
    }


    public static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDatabase() {
        if(!checkDbIfExist()) {
            copyDatabaseFromAssets();
        }
        else
            Log.d(TAG, "DB already exists !!!");
    }

    private boolean checkDbIfExist() {
        File file = new File(AppUtils.getDbPath(mContext) + "/" + AppConstants.DB_NAME);
        if(!file.exists())
        {
            File folder = new File(AppUtils.getDbPath(mContext));
            folder.mkdirs();
        }
        return file.exists();
    }

    private void copyDatabaseFromAssets() {
        InputStream inputStream = null;
        try {
            inputStream = mContext.getAssets().open(AppConstants.DB_NAME);
            String outputFileName = AppUtils.getDbPath(mContext) + AppConstants.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outputFileName);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            Log.d(TAG, "DB Copied successfully !!!");
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }


    @Override
    public Flowable<Story> getStoryById(int id) {
        return Flowable.fromCallable(() -> {
            String mKeyPass = getSP(getFP());

            SQLiteDatabase database = mInstance.getWritableDatabase(decryptString(AppConstants.ENCRYPTED_DB_PASS, mKeyPass));

            Cursor cursor = database.rawQuery(String.format("SELECT * FROM '%s'", STORY_TABLE)
                    + "WHERE Story.id_story = ?", new String[] { String.valueOf(id) });
            Story story = new Story();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    story.setId_story(cursor.getInt(0));
                    story.setTitle(cursor.getString(1));
                    story.setBody(cursor.getString(2));
                    byte[] blobImage = cursor.getBlob(3);
                    if (blobImage != null) {
                        story.setImage(BitmapFactory.decodeByteArray(blobImage, 0, blobImage.length));
                    }
                    story.setIs_favorites(cursor.getInt(4));
                    story.setId_category(cursor.getInt(5));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            database.close();
            return story;
        });
    }

    @Override
    public Flowable<List<Story>> getAllJohaStories() {
        return Flowable.fromCallable(new Callable<List<Story>>() {
            @Override
            public List<Story> call() throws Exception {
                String mKeyPass = getSP(getFP());

                SQLiteDatabase database = mInstance.getWritableDatabase(decryptString(AppConstants.ENCRYPTED_DB_PASS, mKeyPass));

                Cursor cursor = database.rawQuery(String.format("SELECT * FROM '%s'", STORY_TABLE) + "WHERE Story.id_category = ?"
                        , new String[] { "1" });
                List<Story> stories = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Story story = new Story();
                        story.setId_story(cursor.getInt(0));
                        story.setTitle(cursor.getString(1));
                        story.setBody(cursor.getString(2));
                        byte[] blobImage = cursor.getBlob(3);
                        if (blobImage != null) {
                            story.setImage(BitmapFactory.decodeByteArray(blobImage, 0, blobImage.length));
                        }
                        story.setIs_favorites(cursor.getInt(4));
                        story.setId_category(cursor.getInt(5));
                        stories.add(story);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                database.close();
                return stories;
            }
        });
    }

    @Override
    public Observable<List<Story>> getAllGeneralStories() {
        return Observable.fromCallable(new Callable<List<Story>>() {
            @Override
            public List<Story> call() throws Exception {
                String mKeyPass = getSP(getFP());

                SQLiteDatabase database = mInstance.getWritableDatabase(decryptString(AppConstants.ENCRYPTED_DB_PASS, mKeyPass));

                Cursor cursor = database.rawQuery(String.format("SELECT * FROM '%s'", STORY_TABLE) + "WHERE Story.id_category = ?"
                        , new String[] { "2" });
                List<Story> stories = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Story story = new Story();
                        story.setId_story(cursor.getInt(0));
                        story.setTitle(cursor.getString(1));
                        story.setBody(cursor.getString(2));
                        byte[] blobImage = cursor.getBlob(3);
                        if (blobImage != null) {
                            story.setImage(BitmapFactory.decodeByteArray(blobImage, 0, blobImage.length));
                        }
                        story.setIs_favorites(cursor.getInt(4));
                        story.setId_category(cursor.getInt(5));
                        stories.add(story);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                database.close();
                return stories;
            }
        });
    }

    @Override
    public Observable<List<Story>> getAllAnbiaaStories() {
        return Observable.fromCallable(new Callable<List<Story>>() {
            @Override
            public List<Story> call() throws Exception {
                String mKeyPass = getSP(getFP());

                SQLiteDatabase database = mInstance.getWritableDatabase(decryptString(AppConstants.ENCRYPTED_DB_PASS, mKeyPass));

                Cursor cursor = database.rawQuery(String.format("SELECT * FROM '%s'", STORY_TABLE) + "WHERE Story.id_category = ?"
                        , new String[] { "4" });
                List<Story> stories = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Story story = new Story();
                        story.setId_story(cursor.getInt(0));
                        story.setTitle(cursor.getString(1));
                        story.setBody(cursor.getString(2));
                        byte[] blobImage = cursor.getBlob(3);
                        if (blobImage != null) {
                            story.setImage(BitmapFactory.decodeByteArray(blobImage, 0, blobImage.length));
                        }
                        story.setIs_favorites(cursor.getInt(4));
                        story.setId_category(cursor.getInt(5));
                        stories.add(story);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                database.close();
                return stories;
            }
        });
    }

    @Override
    public Observable<List<Story>> getAllAnimalsStories() {
        return Observable.fromCallable(new Callable<List<Story>>() {
            @Override
            public List<Story> call() throws Exception {
                String mKeyPass = getSP(getFP());

                SQLiteDatabase database = mInstance.getWritableDatabase(decryptString(AppConstants.ENCRYPTED_DB_PASS, mKeyPass));

                Cursor cursor = database.rawQuery(String.format("SELECT * FROM '%s'", STORY_TABLE) + "WHERE Story.id_category = ?"
                        , new String[] { "3" });
                List<Story> stories = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Story story = new Story();
                        story.setId_story(cursor.getInt(0));
                        story.setTitle(cursor.getString(1));
                        story.setBody(cursor.getString(2));
                        byte[] blobImage = cursor.getBlob(3);
                        if (blobImage != null) {
                            story.setImage(BitmapFactory.decodeByteArray(blobImage, 0, blobImage.length));
                        }
                        story.setIs_favorites(cursor.getInt(4));
                        story.setId_category(cursor.getInt(5));
                        stories.add(story);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                database.close();
                return stories;
            }
        });
    }

    @Override
    public Single<Boolean> setStoryAsFavourite(int storyId, int valueToSet) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String mKeyPass = getSP(getFP());
                SQLiteDatabase database = mInstance.getWritableDatabase(decryptString(AppConstants.ENCRYPTED_DB_PASS, mKeyPass));
                ContentValues contentValues = new ContentValues();
                contentValues.put(IS_FAVORITE_COLUMN, valueToSet);
                database.update(STORY_TABLE, contentValues, ID_STORY + "= ?", new String[] { String.valueOf(storyId) });
                return true;
            }
        });
    }

    @Override
    public Observable<List<Story>> getAllFavouritesStories() {
        return Observable.fromCallable(new Callable<List<Story>>() {
            @Override
            public List<Story> call() throws Exception {
                String mKeyPass = getSP(getFP());

                SQLiteDatabase database = mInstance.getWritableDatabase(decryptString(AppConstants.ENCRYPTED_DB_PASS, mKeyPass));

                Cursor cursor = database.rawQuery(String.format("SELECT * FROM '%s'", STORY_TABLE) + "WHERE Story.is_favorite = ?"
                        , new String[] { "1" });
                List<Story> stories = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Story story = new Story();
                        story.setId_story(cursor.getInt(0));
                        story.setTitle(cursor.getString(1));
                        story.setBody(cursor.getString(2));
                        byte[] blobImage = cursor.getBlob(3);
                        if (blobImage != null) {
                            story.setImage(BitmapFactory.decodeByteArray(blobImage, 0, blobImage.length));
                        }
                        story.setIs_favorites(cursor.getInt(4));
                        story.setId_category(cursor.getInt(5));
                        stories.add(story);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                database.close();
                return stories;
            }
        });
    }

}
