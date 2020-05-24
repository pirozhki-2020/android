package com.pirozhki.alcohall.recipes.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class OneRecipeFragment extends Fragment {
    private RecyclerView mIngredientsRecyclerView;
    private RecyclerView mStepsRecyclerView;
    private OneRecipeFragment.IngredientAdapter mAdapter;
    private OneRecipeFragment.StepAdapter mStepAdapter;
    private List<FullIngredient> mIngredients;

    private RecipeViewModel mRecipeViewModel;

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one_recipe, container, false);

        mTitleTextView = view.findViewById(R.id.one_recipe_name);
        mDescriptionTextView = view.findViewById(R.id.one_recipe_description);

        mIngredientsRecyclerView = view.findViewById(R.id.one_recipe_recycler_view);
        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(requireActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mIngredientsRecyclerView.setLayoutManager(ingredientLayoutManager);
        mAdapter = new OneRecipeFragment.IngredientAdapter();
        mIngredientsRecyclerView.setAdapter(mAdapter);
        mStepsRecyclerView = view.findViewById(R.id.one_recipe_steps_recycler_view);
        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(requireActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mStepsRecyclerView.setLayoutManager(stepsLayoutManager);
        mStepAdapter = new OneRecipeFragment.StepAdapter();
        mStepsRecyclerView.setAdapter(mStepAdapter);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeViewModel.getOneRecipeApiResponse().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getRecipe());
            }
        });

        Bundle args = getArguments();
        if (args != null && OneRecipeFragmentArgs.fromBundle(args).getRecipeId() != -1) {
            mRecipeViewModel.findOneRecipe(String.valueOf(OneRecipeFragmentArgs.fromBundle(args).getRecipeId()));
        }

        return view;
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

    private class IngredientAdapter extends RecyclerView.Adapter<OneRecipeFragment.IngredientHolder> {
        private List<FullIngredient> mIngredients = new ArrayList<>();

        @Override
        @NonNull
        public OneRecipeFragment.IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(OneRecipeFragment.this.requireActivity());
            View view = layoutInflater.inflate(R.layout.list_item_one_recipe_ingredient, parent, false);
            return new OneRecipeFragment.IngredientHolder(view);
        }

        @Override
        public void onBindViewHolder(OneRecipeFragment.IngredientHolder holder, int position) {
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

    private class StepAdapter extends RecyclerView.Adapter<OneRecipeFragment.StepHolder> {
        private List<String> mSteps = new ArrayList<>();

        @Override
        @NonNull
        public OneRecipeFragment.StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(OneRecipeFragment.this.requireActivity());
            View view = layoutInflater.inflate(R.layout.list_item_one_step, parent, false);
            return new OneRecipeFragment.StepHolder(view);
        }

        @Override
        public void onBindViewHolder(OneRecipeFragment.StepHolder holder, int position) {
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
