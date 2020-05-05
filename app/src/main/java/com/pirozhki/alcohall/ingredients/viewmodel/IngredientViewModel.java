package com.pirozhki.alcohall.ingredients.viewmodel;

import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.repository.IngredientRepository;

import java.util.List;

public class IngredientViewModel extends ViewModel {
    private IngredientRepository mIngredientRepository;

    public IngredientViewModel() {
        mIngredientRepository = IngredientRepository.getInstance();
    }

    public List<Ingredient> getIngredients() {
        return mIngredientRepository.getIngredients();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredientRepository.setIngredients(ingredients);
    }

    public void addIngredient(Ingredient ingredient) {
        mIngredientRepository.addIngredient(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        mIngredientRepository.removeIngredient(ingredient);
    }

    public void clearIngredients() {
        mIngredientRepository.clearIngredients();
    }

    public boolean areIngredientsEmpty() {
        return mIngredientRepository.areIngredientsEmpty();
    }

    public int[] getIngredientsIds() {
        return mIngredientRepository.getIngredientsIds();
    }
}
