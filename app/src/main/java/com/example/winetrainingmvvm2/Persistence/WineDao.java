package com.example.winetrainingmvvm2.Persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Wine;

import java.util.List;

@Dao
public interface WineDao {

    @Insert
    void insert(Wine wine);
    @Insert
    void insert(Question question);

    @Update
    void update(Wine wine);
    @Update
    void update(Question question);

    @Delete
    void delete(Wine wine);
    @Delete
    void delete(Question question);

    @Query("DELETE FROM table_wine")
    void deleteAllWines();
    @Query("DELETE FROM table_question")
    void deleteAllQuestions();

    @Query("SELECT * FROM table_wine ORDER BY category, name")
    LiveData<List<Wine>> getAllWines();

    @Query("SELECT * FROM table_question ORDER BY type,question")
    LiveData<List<Question>> getAllQuestions();

    //TODO try making 4 get all with different order by to do filtering on winelist page?

}
