package com.example.winetrainingmvvm2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.Repositories.WineRepository;

import java.util.List;

public class WineViewModel extends AndroidViewModel {
    private static final String TAG = "WineViewModel";

    //Todo some type of checking to see what is selected on the alertview

    private WineRepository repository;
    private LiveData<List<Wine>> allWines;


    //Constructor
    public WineViewModel(@NonNull Application application) {
        super(application);
        this.repository = new WineRepository(application);
        this.allWines = repository.getAllWines();
    }

    //Database Querying
    public void insert(Wine wine) {
        repository.insert(wine);
    }

    public void update(Wine wine) {
        repository.update(wine);
    }

    public void delete(Wine wine) {
        repository.delete(wine);
    }

    public void deleteAllWines() {
        repository.deleteAllWines();
    }

    //LiveData
    public LiveData<List<Wine>> getAllWines() {
        return allWines;
    }

}
