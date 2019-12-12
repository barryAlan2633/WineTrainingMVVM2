package com.example.winetrainingmvvm2.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.Persistence.WineDao;
import com.example.winetrainingmvvm2.Persistence.WineDatabase;

import java.util.List;

public class WineRepository {
    private static final String TAG = "WineRepository";

    private WineDao wineDao;
    private LiveData<List<Wine>> allWines;

    public WineRepository(WineDao wineDao, LiveData<List<Wine>> allWines, LiveData<List<Question>> allQuestions) {
        this.wineDao = wineDao;
        this.allWines = allWines;
    }

    public WineRepository(Application application) {
        WineDatabase database = WineDatabase.getInstance(application);
        this.wineDao = database.wineDao();
        this.allWines = wineDao.getAllWines();
    }

    public void insert(Wine wine) {
        new InsertWineAsyncTask(wineDao).execute(wine);
    }

    public void update(Wine wine) {
        new UpdateWineAsyncTask(wineDao).execute(wine);
    }

    public void delete(Wine wine) {
        new DeleteWineAsyncTask(wineDao).execute(wine);
    }

    public void deleteAllWines() {
        new DeleteAllWinesAsyncTask(wineDao).execute();
    }

    public static class InsertWineAsyncTask extends AsyncTask<Wine, Void, Void> {
        private WineDao wineDao;

        public InsertWineAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Wine... wines) {
            wineDao.insert(wines[0]);
            return null;
        }
    }

    static private class UpdateWineAsyncTask extends AsyncTask<Wine, Void, Void> {
        private WineDao wineDao;

        public UpdateWineAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Wine... wines) {
            wineDao.update(wines[0]);

            return null;
        }
    }

    static private class DeleteWineAsyncTask extends AsyncTask<Wine, Void, Void> {
        private WineDao wineDao;

        public DeleteWineAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Wine... wines) {
            wineDao.delete(wines[0]);
            return null;
        }
    }

    static private class DeleteAllWinesAsyncTask extends AsyncTask<Wine, Void, Void> {
        private WineDao wineDao;

        public DeleteAllWinesAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Wine... wines) {
            wineDao.deleteAllWines();
            return null;
        }
    }

    public LiveData<List<Wine>> getAllWines() {
        return allWines;
    }


}
