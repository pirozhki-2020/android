package com.pirozhki.alcohall.ingredients.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pirozhki.alcohall.ingredients.model.Ingredient;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface IngredientsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Ingredient... ingredients);

    @Delete
    void delete(Ingredient ingredient);

    @Query("SELECT * FROM ingredients")
    Flowable<List<Ingredient>> getAllIngredients();

    @Query("SELECT id FROM ingredients")
    Single<List<Integer>> getIngredientsIds();

    @Query("DELETE FROM ingredients")
    void clear();
}
