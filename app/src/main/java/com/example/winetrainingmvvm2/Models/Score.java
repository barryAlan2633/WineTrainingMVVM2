package com.example.winetrainingmvvm2.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_score")
public class Score {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "score")
    private int score;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "place")
    private int place;

    @ColumnInfo(name = "type")
    private int type;

    public Score(int score, String name, int place, int type) {
        this.score = score;
        this.place = place;
        this.type = type;
        this.name = name;
    }

    public String getName() { return name; }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                ", name='" + name + '\'' +
                ", place=" + place +
                ", type=" + type +
                '}';
    }
}
