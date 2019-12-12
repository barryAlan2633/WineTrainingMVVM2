package com.example.winetrainingmvvm2.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_wine")
public class Wine {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "glassPrice")
    private float glassPrice;

    @ColumnInfo(name = "bottlePrice")
    private float bottlePrice;

    public Wine(String name, String category, float glassPrice, float bottlePrice) {
        this.name = name;
        this.category = category;
        this.glassPrice = glassPrice;
        this.bottlePrice = bottlePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getGlassPrice() {
        return glassPrice;
    }

    public float getBottlePrice() {
        return bottlePrice;
    }
}
