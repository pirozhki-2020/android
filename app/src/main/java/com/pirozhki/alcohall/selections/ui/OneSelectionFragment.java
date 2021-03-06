package com.pirozhki.alcohall.selections.ui;

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
import com.pirozhki.alcohall.recipes.model.Recipe;
import com.pirozhki.alcohall.recipes.ui.OneRecipeFragmentArgs;
import com.pirozhki.alcohall.recipes.ui.RecipesFragmentDirections;
import com.pirozhki.alcohall.selections.model.Selection;
import com.pirozhki.alcohall.selections.network.SelectionApi;
import com.pirozhki.alcohall.selections.viewmodel.SelectionViewModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class OneSelectionFragment extends Fragment {
    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter = new RecipeAdapter();

    private SelectionViewModel mSelectionViewModel;
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one_selection, null);

        mSelectionViewModel = new ViewModelProvider(this).get(SelectionViewModel.class);


        mTitleTextView = view.findViewById(R.id.one_selection_name);
        mDescriptionTextView = view.findViewById(R.id.one_selection_description);


        mRecipeRecyclerView = view.findViewById(R.id.one_selection_recipes_recycler_view);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mAdapter = new RecipeAdapter();
        mRecipeRecyclerView.setAdapter(mAdapter);

        mSelectionViewModel.getOneSelectionApiResponse().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getSelection());
            }
        });

        Bundle args = getArguments();
        if (args != null && OneSelectionFragmentArgs.fromBundle(args).getSelectionId() != -1) {
            mSelectionViewModel.getOneSelection(String.valueOf(OneSelectionFragmentArgs.fromBundle(args).getSelectionId()));
        }

        return view;
    }

    public void onRecipeSelected(Recipe recipe) {
        findNavController(this).navigate(OneSelectionFragmentDirections.toOneRecipeFragmentFromSelection()
                .setRecipeId(recipe.getId()));
    }

    private void handleError(Throwable error) {
        Log.e(SelectionsFragment.class.getName(), "error occurred while get api response: " + error.toString());
    }

    private void handleResponse(Selection selection) {
        if (selection.getRecipes() != null && selection.getRecipes().size() > 0) {
            mAdapter.setRecipes(selection.getRecipes());
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.clearRecipes();
            mAdapter.notifyDataSetChanged();
        }
        if (selection.getName() != null) {
            mTitleTextView.setText(selection.getName());
        }
        if (selection.getDescription() != null) {
            mDescriptionTextView.setText(selection.getDescription());
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
            LayoutInflater layoutInflater = LayoutInflater.from(OneSelectionFragment.this.getActivity());
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
