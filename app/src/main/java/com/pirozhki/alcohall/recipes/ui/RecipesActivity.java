package com.pirozhki.alcohall.recipes.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.ui.AddIngredientDialogFragment;
import com.pirozhki.alcohall.ingredients.viewmodel.IngredientViewModel;
import com.pirozhki.alcohall.recipes.model.Recipe;
import com.pirozhki.alcohall.recipes.viewmodel.RecipeViewModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipesActivity extends AppCompatActivity {
    private RecyclerView mRecipesRecyclerView;
    private RecipesActivity.RecipeAdapter mAdapter = new RecipeAdapter();
    private List<Recipe> mRecipes;

    private RecipeViewModel mRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_activity);

        mRecipesRecyclerView = findViewById(R.id.recipes_recycler_view);
        mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecipesRecyclerView.setAdapter(mAdapter);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getRecipes());
            }
        });

        int [] ids = {19};
        mRecipeViewModel.findRecipes(ids);
        /*Thread th = new Thread(new Runnable() {
                public void run() {
                    int [] ids = {10};
                    mRecipeViewModel.findRecipes(ids);
                };
            }
        );
        th.start();*/
    }


    private void handleError(Throwable error) {
        Log.e(AddIngredientDialogFragment.class.getName(), "error occurred while get api response: " + error.toString());
    }

    private void handleResponse(List<Recipe> recipes) {
        if (recipes != null && recipes.size() > 0) {
            mAdapter.setRecipes(recipes);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.clearRecipes();
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RecipeHolder extends RecyclerView.ViewHolder {
        private Recipe mRecipe;

        private TextView mTitleTextView;
        private TextView mStrengthTextView;

        public RecipeHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.recipe_title_text_view);
            mStrengthTextView = itemView.findViewById(R.id.recipe_strength_text_view);
        }

        public void bindRecipe(Recipe recipe) {
            mRecipe = recipe;
            mTitleTextView.setText(mRecipe.mName);
            //mVolumeTextView.setText(String.valueOf(mIngredient.getVolumeMl()));
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipesActivity.RecipeHolder> {
        private List<Recipe> mRecipes = new ArrayList<>();

        @Override
        @NonNull
        public RecipesActivity.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(RecipesActivity.this);
            View view = layoutInflater.inflate(R.layout.list_item_recipe, parent, false);
            return new RecipesActivity.RecipeHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipesActivity.RecipeHolder holder, int position) {
            Recipe recipe = mRecipes.get(position);
            holder.bindRecipe(recipe);
        }

        @Override
        public int getItemCount() {
            return mRecipes.size();
        }

        public void setRecipes(List<Recipe> recipes) {
            mRecipes = recipes;
        }

        void clearRecipes() {
            mRecipes.clear();
        }
    }
}
