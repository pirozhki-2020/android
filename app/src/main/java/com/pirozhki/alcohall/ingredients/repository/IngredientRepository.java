package com.pirozhki.alcohall.ingredients.repository;

import com.pirozhki.alcohall.ingredients.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientRepository {
    private List<Ingredient> mIngredients;
    private final static IngredientRepository INSTANCE = new IngredientRepository();

    private IngredientRepository() {
        mIngredients = new ArrayList<>();
    }

    public static IngredientRepository getInstance() {
        return INSTANCE;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        mIngredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        mIngredients.remove(ingredient);
    }

    public void clearIngredients() {
        mIngredients.clear();
    }

    public boolean areIngredientsEmpty() {
        return mIngredients.isEmpty();
    }
}
