package com.pirozhki.alcohall.likes.ui;

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
import com.pirozhki.alcohall.likes.network.LikesApi;
import com.pirozhki.alcohall.likes.viewmodel.LikesViewModel;
import com.pirozhki.alcohall.recipes.model.Recipe;
import com.pirozhki.alcohall.recipes.ui.RecipesFragment;
import com.pirozhki.alcohall.recipes.ui.RecipesFragmentDirections;
import com.pirozhki.alcohall.recipes.viewmodel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class LikesFragment extends Fragment {

    private RecyclerView mRecipesRecyclerView;
    private RecipeAdapter mAdapter = new RecipeAdapter();

    private LikesViewModel mLikesViewModel;

    private TextView mNoResultsTextView;
    private TextView mNotAutoTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_likes, container, false);

        mNoResultsTextView = view.findViewById(R.id.no_likes_text_view);
        mNotAutoTextView = view.findViewById(R.id.not_autorized_text_view);

        mRecipesRecyclerView = view.findViewById(R.id.likes_recycler_view);
        mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecipesRecyclerView.setAdapter(mAdapter);

        mLikesViewModel = new ViewModelProvider(this).get(LikesViewModel.class);
        mLikesViewModel.getApiResponse().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getRecipes());
            }
        });

        mLikesViewModel.findRecipes();

        return view;
    }

    public void onRecipeSelected(Recipe recipe) {
        findNavController(this).navigate(LikesFragmentDirections.toOneRecipeFragmentFromLikes()
                .setRecipeId(recipe.getId()));
    }

    private void handleError(Throwable error) {
        Log.e(AddIngredientDialogFragment.class.getName(), "error occurred while get api response: " + error.toString());
        mAdapter.clearRecipes();
        mAdapter.notifyDataSetChanged();
        mNotAutoTextView.setVisibility(View.VISIBLE);
    }

    private void handleResponse(List<Recipe> recipes) {
        if (recipes != null && recipes.size() > 0) {
            mAdapter.setRecipes(recipes);
            mAdapter.notifyDataSetChanged();
            mNoResultsTextView.setVisibility(View.GONE);
            mNotAutoTextView.setVisibility(View.GONE);
        } else {
            mAdapter.clearRecipes();
            mAdapter.notifyDataSetChanged();
            mNoResultsTextView.setVisibility(View.VISIBLE);
            mNotAutoTextView.setVisibility(View.GONE);
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
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
        private List<Recipe> mRecipes = new ArrayList<>();

        @Override
        @NonNull
        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(LikesFragment.this.requireActivity());
            View view = layoutInflater.inflate(R.layout.list_item_recipe, parent, false);
            return new RecipeHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {
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
