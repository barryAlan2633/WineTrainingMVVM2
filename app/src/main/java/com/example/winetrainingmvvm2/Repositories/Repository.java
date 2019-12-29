package com.example.winetrainingmvvm2.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Score;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.Persistence.WineDao;
import com.example.winetrainingmvvm2.Persistence.WineDatabase;

import java.util.List;

public class Repository {
    private static final String TAG = "Repository";

    private WineDao mWineDao;
    private LiveData<List<Wine>> mAllWines;
    private LiveData<List<Question>> mAllQuestions;
    private LiveData<List<Score>> mAllScores;

    public Repository(WineDao wineDao, LiveData<List<Wine>> allWines, LiveData<List<Question>> allQuestions) {
        this.mWineDao = wineDao;
        this.mAllWines = allWines;
        this.mAllQuestions = allQuestions;
    }

    public Repository(Application application) {
        WineDatabase database = WineDatabase.getInstance(application);
        this.mWineDao = database.wineDao();
        this.mAllWines = mWineDao.getAllWines();
        this.mAllQuestions = mWineDao.getAllQuestions();
        this.mAllScores = mWineDao.getAllScores();
    }

    public void insert(Wine wine) {
        new InsertWineAsyncTask(mWineDao).execute(wine);
    }
    public void insert(Question question) {
        new InsertQuestionAsyncTask(mWineDao).execute(question);
    }
    public void insert(Score score) {
        new InsertScoreAsyncTask(mWineDao).execute(score);
    }

    public void update(Wine wine) {
        new UpdateWineAsyncTask(mWineDao).execute(wine);
    }
    public void update(Question question) {
        new UpdateQuestionAsyncTask(mWineDao).execute(question);
    }
    public void update(Score score) {
        new UpdateScoreAsyncTask(mWineDao).execute(score);
    }


    public void delete(Wine wine) {
        new DeleteWineAsyncTask(mWineDao).execute(wine);
    }
    public void delete(Question question) {
        new DeleteQuestionAsyncTask(mWineDao).execute(question);
    }
    public void delete(Score score) {
        new DeleteScoreAsyncTask(mWineDao).execute(score);
    }

    public void deleteAllWines() {
        new DeleteAllWinesAsyncTask(mWineDao).execute();
    }
    public void deleteAllQuestions() {
        new DeleteAllQuestionsAsyncTask(mWineDao).execute();
    }
    public void deleteAllScores() {
        new DeleteAllScoresAsyncTask(mWineDao).execute();
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
    public static class InsertQuestionAsyncTask extends AsyncTask<Question, Void, Void> {
        private WineDao wineDao;

        public InsertQuestionAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            wineDao.insert(questions[0]);
            return null;
        }
    }
    public static class InsertScoreAsyncTask extends AsyncTask<Score, Void, Void> {
        private WineDao wineDao;

        public InsertScoreAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Score... scores) {
            wineDao.insert(scores[0]);
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
    static private class UpdateQuestionAsyncTask extends AsyncTask<Question, Void, Void> {
        private WineDao wineDao;

        public UpdateQuestionAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            wineDao.update(questions[0]);

            return null;
        }
    }
    static private class UpdateScoreAsyncTask extends AsyncTask<Score, Void, Void> {
        private WineDao wineDao;

        public UpdateScoreAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Score... scores) {
            wineDao.update(scores[0]);

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
    static private class DeleteQuestionAsyncTask extends AsyncTask<Question, Void, Void> {
        private WineDao wineDao;

        public DeleteQuestionAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            wineDao.delete(questions[0]);
            return null;
        }
    }
    static private class DeleteScoreAsyncTask extends AsyncTask<Score, Void, Void> {
        private WineDao wineDao;

        public DeleteScoreAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Score... scores) {
            wineDao.delete(scores[0]);
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
    static private class DeleteAllQuestionsAsyncTask extends AsyncTask<Question, Void, Void> {
        private WineDao wineDao;

        public DeleteAllQuestionsAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            wineDao.deleteAllQuestions();
            return null;
        }
    }
    static private class DeleteAllScoresAsyncTask extends AsyncTask<Score, Void, Void> {
        private WineDao wineDao;

        public DeleteAllScoresAsyncTask(WineDao wineDao) {
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Score... scores) {
            wineDao.deleteAllScores();
            return null;
        }
    }

    public LiveData<List<Wine>> getAllWines() {
        return mAllWines;
    }
    public LiveData<List<Question>> getAllQuestions() {
        return mAllQuestions;
    }
    public LiveData<List<Score>> getAllScores() {
        return mAllScores;
    }


}
