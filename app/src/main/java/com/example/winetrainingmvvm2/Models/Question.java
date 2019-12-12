package com.example.winetrainingmvvm2.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_question")
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "question")
    private String question;

    public Question(int type, String question) {
        this.type = type;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }
}
