package com.example.winetrainingmvvm2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.Repositories.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private static final String TAG = "ViewModel";

    //Todo some type of checking to see what is selected on the alertview

    private Repository mRepository;
    private LiveData<List<Wine>> mAllWines;
    private LiveData<List<Question>> mAllQuestions;


    //Constructor
    public ViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = new Repository(application);
        this.mAllWines = mRepository.getAllWines();
        this.mAllQuestions = mRepository.getAllQuestions();
    }

    //Database Querying
    public void insert(Wine wine) {
        mRepository.insert(wine);
    }
    public void insert(Question question) {
        mRepository.insert(question);
    }

    public void update(Wine wine) {
        mRepository.update(wine);
    }
    public void update(Question question) {
        mRepository.update(question);
    }

    public void delete(Wine wine) {
        mRepository.delete(wine);
    }
    public void delete(Question question) {
        mRepository.delete(question);
    }

    public void deleteAllWines() {
        mRepository.deleteAllWines();
    }
    public void deleteAllQuestions() {
        mRepository.deleteAllQuestions();
    }

    //LiveData
    public LiveData<List<Wine>> getAllWines() {
        return mAllWines;
    }
    public LiveData<List<Question>> getAllQuestions() {
        return mAllQuestions;
    }

}
