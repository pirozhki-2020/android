package com.pirozhki.alcohall.recipes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.ui.AddIngredientDialogFragment;
import com.pirozhki.alcohall.recipes.model.Recipe;
import com.pirozhki.alcohall.recipes.viewmodel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends Fragment {
    private RecyclerView mRecipesRecyclerView;
    private RecipesFragment.RecipeAdapter mAdapter = new RecipeAdapter();

    private RecipeViewModel mRecipeViewModel;

    private TextView mNoResultsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        mNoResultsTextView = view.findViewById(R.id.no_recipes_text_view);

        mRecipesRecyclerView = view.findViewById(R.id.recipes_recycler_view);
        mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecipesRecyclerView.setAdapter(mAdapter);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeViewModel.getApiResponse().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getRecipes());
            }
        });

        /*final Button backFromAddButton = view.findViewById(R.id.back_from_recipes_button);
        backFromAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        ArrayList<Integer> ids = intent.getIntegerArrayListExtra("ids");
        mRecipeViewModel.findRecipes(ids);*/
        return view;
    }

    public void onRecipeSelected(Recipe recipe) {
        /*Intent intent = new Intent(this, OneRecipeActivity.class);
        intent.putExtra("id", recipe.getId());
        startActivity(intent);*/
    }

    private void handleError(Throwable error) {
        Log.e(AddIngredientDialogFragment.class.getName(), "error occurred while get api response: " + error.toString());
    }

    private void handleResponse(List<Recipe> recipes) {
        if (recipes != null && recipes.size() > 0) {
            mAdapter.setRecipes(recipes);
            mAdapter.notifyDataSetChanged();
            mNoResultsTextView.setVisibility(View.INVISIBLE);
        } else {
            mAdapter.clearRecipes();
            mAdapter.notifyDataSetChanged();
            mNoResultsTextView.setVisibility(View.VISIBLE);
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

            mTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecipeSelected(mRecipe);
                }
            });
        }

        public void bindRecipe(Recipe recipe) {
            mRecipe = recipe;
            mTitleTextView.setText(mRecipe.getName());
            //mVolumeTextView.setText(String.valueOf(mIngredient.getVolumeMl()));
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipesFragment.RecipeHolder> {
        private List<Recipe> mRecipes = new ArrayList<>();

        @Override
        @NonNull
        public RecipesFragment.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(RecipesFragment.this.requireActivity());
            View view = layoutInflater.inflate(R.layout.list_item_recipe, parent, false);
            return new RecipesFragment.RecipeHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipesFragment.RecipeHolder holder, int position) {
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
