package com.pirozhki.alcohall.common;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.repository.IngredientsDao;

@Database(entities = {Ingredient.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IngredientsDao getIngredientsDao();
}
