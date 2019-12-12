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

    @Update
    void update(Wine wine);

    @Delete
    void delete(Wine wine);

    @Query("DELETE FROM table_wine")
    void deleteAllWines();

    @Query("SELECT * FROM table_wine ORDER BY category, name")
    LiveData<List<Wine>> getAllWines();
    //TODO try making 4 get all with different order by to do filtering on winelist page?

}
