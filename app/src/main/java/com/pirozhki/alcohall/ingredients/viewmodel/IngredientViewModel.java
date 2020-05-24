package com.pirozhki.alcohall.ingredients.viewmodel;

import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.repository.IngredientRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class IngredientViewModel extends ViewModel {
    private IngredientRepository mIngredientRepository;

    public IngredientViewModel() {
        mIngredientRepository = IngredientRepository.getInstance();
    }

    public Flowable<List<Ingredient>> getIngredients() {
        return mIngredientRepository.getIngredients();
    }

    public Completable addIngredient(Ingredient ingredient) {
        return mIngredientRepository.addIngredient(ingredient);
    }

    public Completable removeIngredient(Ingredient ingredient) {
        return mIngredientRepository.removeIngredient(ingredient);
    }

    public Completable clearIngredients() {
        return mIngredientRepository.clearIngredients();
    }

    public Single<List<Integer>> getIngredientsIds() {
        return mIngredientRepository.getIngredientsIds();
    }
}
