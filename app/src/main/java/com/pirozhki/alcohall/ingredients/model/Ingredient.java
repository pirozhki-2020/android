package com.pirozhki.alcohall.ingredients.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

@Entity(tableName = "ingredients")
public class Ingredient {
    @Json(name = "id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer mId;

    @Json(name = "name")
    @ColumnInfo(name = "name")
    private String mName;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
