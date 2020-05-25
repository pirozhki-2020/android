package com.pirozhki.alcohall.ingredients.repository;

import com.pirozhki.alcohall.common.App;
import com.pirozhki.alcohall.ingredients.model.Ingredient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class IngredientRepository {
    private IngredientsDao mIngredientsDao;
    private static final IngredientRepository INSTANCE = new IngredientRepository();

    private IngredientRepository() {
        mIngredientsDao = App.getInstance().getDatabase().getIngredientsDao();
    }

    public static IngredientRepository getInstance() {
        return INSTANCE;
    }

    public Flowable<List<Ingredient>> getIngredients() {
        return mIngredientsDao.getAllIngredients();
    }

    public Completable addIngredient(Ingredient ingredient) {
        return Completable
                .fromAction(() -> mIngredientsDao.insertAll(ingredient))
                .subscribeOn(Schedulers.single());
    }

    public Completable removeIngredient(Ingredient ingredient) {
        return Completable
                .fromAction(() -> mIngredientsDao.delete(ingredient))
                .subscribeOn(Schedulers.single());
    }

    public Completable clearIngredients() {
        return Completable
                .fromAction(() -> mIngredientsDao.clear())
                .subscribeOn(Schedulers.single());
    }

    public Single<List<Integer>> getIngredientsIds() {
        return mIngredientsDao.getIngredientsIds();
    }
}
