package com.pirozhki.alcohall.recipes.ui;

import android.content.Intent;
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
import com.pirozhki.alcohall.ingredients.ui.AddIngredientDialogFragment;
import com.pirozhki.alcohall.recipes.model.FullIngredient;
import com.pirozhki.alcohall.recipes.model.FullRecipe;
import com.pirozhki.alcohall.recipes.model.Step;
import com.pirozhki.alcohall.recipes.viewmodel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class OneRecipeActivity extends AppCompatActivity {
    private RecyclerView mIngredientsRecyclerView;
    private RecyclerView mStepsRecyclerView;
    private OneRecipeActivity.IngredientAdapter mAdapter;
    private OneRecipeActivity.StepAdapter mStepAdapter;
    private List<FullIngredient> mIngredients;

    private RecipeViewModel mRecipeViewModel;

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_recipe_activity);

        mTitleTextView = findViewById(R.id.one_recipe_name);
        mDescriptionTextView = findViewById(R.id.one_recipe_description);

        mIngredientsRecyclerView = findViewById(R.id.one_recipe_recycler_view);
        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mIngredientsRecyclerView.setLayoutManager(ingredientLayoutManager);
        mAdapter = new OneRecipeActivity.IngredientAdapter();
        mIngredientsRecyclerView.setAdapter(mAdapter);
        mStepsRecyclerView = findViewById(R.id.one_recipe_steps_recycler_view);
        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mStepsRecyclerView.setLayoutManager(stepsLayoutManager);
        mStepAdapter = new OneRecipeActivity.StepAdapter();
        mStepsRecyclerView.setAdapter(mStepAdapter);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeViewModel.getOneRecipeApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getRecipe());
            }
        });

        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id", 0);
        mRecipeViewModel.findOneRecipe(String.valueOf(id));
    }

    private void handleError(Throwable error) {
        Log.e(AddIngredientDialogFragment.class.getName(), "error occurred while get api response: " + error.toString());
    }

    private void handleResponse(FullRecipe recipe) {
        if (recipe != null) {
            mAdapter.setIngredients(recipe.getIngredients());
            mAdapter.notifyDataSetChanged();
            mStepAdapter.setSteps(recipe.getSteps());
            mStepAdapter.notifyDataSetChanged();

            if (recipe.getName() != null) {
                mTitleTextView.setText(recipe.getName());
            }
            if (recipe.getDescription() != null) {
                mDescriptionTextView.setText(recipe.getDescription());
            }
        }
    }

    private class IngredientHolder extends RecyclerView.ViewHolder {
        private FullIngredient mIngredient;

        private TextView mTitleTextView;
        private TextView mVolumeTextView;

        IngredientHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.one_recipe_ingredient_title);
            mVolumeTextView = itemView.findViewById(R.id.one_recipe_ingredient_volume);
        }

        void bindIngredient(FullIngredient ingredient) {
            mIngredient = ingredient;
            mTitleTextView.setText(mIngredient.getName());
            mVolumeTextView.setText(String.valueOf(mIngredient.getVolume()));
        }
    }

    private class IngredientAdapter extends RecyclerView.Adapter<OneRecipeActivity.IngredientHolder> {
        private List<FullIngredient> mIngredients = new ArrayList<>();

        @Override
        @NonNull
        public OneRecipeActivity.IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(OneRecipeActivity.this);
            View view = layoutInflater.inflate(R.layout.list_item_one_recipe_ingredient, parent, false);
            return new OneRecipeActivity.IngredientHolder(view);
        }

        @Override
        public void onBindViewHolder(OneRecipeActivity.IngredientHolder holder, int position) {
            FullIngredient ingredient = mIngredients.get(position);
            holder.bindIngredient(ingredient);
        }

        @Override
        public int getItemCount() {
            return mIngredients.size();
        }

        public void setIngredients(List<FullIngredient> ingredients) {
            mIngredients = ingredients;
        }
    }

    private class StepHolder extends RecyclerView.ViewHolder {
        private Step mStep;

        private TextView mDescriptionTextView;
        private TextView mNumberTextView;

        StepHolder(View itemView) {
            super(itemView);

            mDescriptionTextView = itemView.findViewById(R.id.one_recipe_step_title);
            mNumberTextView = itemView.findViewById(R.id.one_recipe_step_number);
        }

        void bindStep(Step step) {
            mStep = step;
            mDescriptionTextView.setText(mStep.getDescription());
            mNumberTextView.setText(String.valueOf(mStep.getNumber()));
        }
    }

    private class StepAdapter extends RecyclerView.Adapter<OneRecipeActivity.StepHolder> {
        private List<String> mSteps = new ArrayList<>();

        @Override
        @NonNull
        public OneRecipeActivity.StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(OneRecipeActivity.this);
            View view = layoutInflater.inflate(R.layout.list_item_one_step, parent, false);
            return new OneRecipeActivity.StepHolder(view);
        }

        @Override
        public void onBindViewHolder(OneRecipeActivity.StepHolder holder, int position) {
            Step step = new Step();
            step.setNumber(position + 1);
            step.setDescription(mSteps.get(position));
            holder.bindStep(step);
        }

        @Override
        public int getItemCount() {
            return mSteps.size();
        }

        public void setSteps(List<String> steps) {
            mSteps = steps;
        }
    }
}
