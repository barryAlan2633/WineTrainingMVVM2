package com.example.winetrainingmvvm2.Persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Wine;

@Database(entities = {Wine.class, Question.class},version = 2)
public abstract class WineDatabase extends RoomDatabase {
    private static final String TAG = "WineDatabase";

    public abstract WineDao wineDao();

    //Singleton
    private static WineDatabase instance;
    public static synchronized WineDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WineDatabase.class,"wine_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        PopulateDbAsyncTask(WineDatabase db){
            WineDao wineDao = db.wineDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
