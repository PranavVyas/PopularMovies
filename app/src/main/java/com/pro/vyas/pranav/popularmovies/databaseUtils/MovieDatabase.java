package com.pro.vyas.pranav.popularmovies.databaseUtils;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;

@Database(entities = MovieEntry.class, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase{

    public static final String DB_NAME = "Favourite_Movies";
    public static final Object LOCK = new Object();
    public static MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context){
        if(sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, MovieDatabase.DB_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();

}
