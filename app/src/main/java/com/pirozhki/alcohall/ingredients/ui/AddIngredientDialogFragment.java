package com.pirozhki.alcohall.ingredients.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.viewmodel.AddIngredientViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class AddIngredientDialogFragment extends DialogFragment {
    private IngredientAdapter mAdapter;
    private AddIngredientViewModel mAddIngredientViewModel;
    private TextView mNoResultsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_ingredient, container, false);
        mNoResultsTextView = view.findViewById(R.id.no_results_text_view);

        mAddIngredientViewModel = new ViewModelProvider(requireActivity()).get(AddIngredientViewModel.class);

        RecyclerView ingredientRecyclerView = view.findViewById(R.id.add_ingredient_recycler_view);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(mAdapter);

        final Button backFromAddButton = view.findViewById(R.id.back_from_add_button);
        backFromAddButton.setOnClickListener(v -> {
            mAdapter.clearIngredients();
            mAdapter.notifyDataSetChanged();
            findNavController(this).navigate(AddIngredientDialogFragmentDirections
                    .backFromAddIngredientToIngredientsFragment());
        });

        final TextInputEditText findIngredient = view.findViewById(R.id.searchIngredientEditText);

        assert findIngredient != null;
        findIngredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = Objects.requireNonNull(Objects.requireNonNull(findIngredient).getText()).toString();
                if (query.isEmpty()) {
                    AddIngredientDialogFragment.this.showNoResult();
                } else {
                    mNoResultsTextView.setVisibility(View.INVISIBLE);
                    mAddIngredientViewModel.findIngredients(query);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Handle changes emitted by LiveData
        mAddIngredientViewModel.getApiResponse().observe(requireActivity(), apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getIngredients());
            }
        });

        return view;
    }

    private void showIngredients(List<Ingredient> ingredients) {
        mAdapter.setIngredients(ingredients);
        mAdapter.notifyDataSetChanged();
        mNoResultsTextView.setVisibility(View.INVISIBLE);
    }

    private void showNoResult() {
        mAdapter.clearIngredients();
        mAdapter.notifyDataSetChanged();
        mNoResultsTextView.setVisibility(View.VISIBLE);
    }

    private void handleError(Throwable error) {
        showNoResult();
        Log.e(AddIngredientDialogFragment.class.getName(), "error occurred while get api response: " + error.toString());
    }

    private void handleResponse(List<Ingredient> ingredients) {
        if (ingredients != null && ingredients.size() > 0)
            showIngredients(ingredients);
        else
            showNoResult();
    }

    private class IngredientHolder extends RecyclerView.ViewHolder {
        private Ingredient mIngredient;

        private Chip mIngredientChip;

        IngredientHolder(View itemView) {
            super(itemView);

            mIngredientChip = itemView.findViewById(R.id.add_ingredient_chip);

            mIngredientChip.setOnClickListener(v -> {
                mAdapter.clearIngredients();
                mAdapter.notifyDataSetChanged();
                findNavController(AddIngredientDialogFragment.this)
                        .navigate(AddIngredientDialogFragmentDirections.toIngredientsFragment()
                                .setIngredientId(mIngredient.getId()).setIngredientName(mIngredient.getName()));
            });
        }

        void bindIngredient(Ingredient ingredient) {
            mIngredient = ingredient;
            mIngredientChip.setText(mIngredient.getName());
        }
    }

    private class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder> {
        private List<Ingredient> mIngredients = new ArrayList<>();

        @Override
        @NonNull
        public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(AddIngredientDialogFragment.this.getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_add_ingredient, parent, false);
            return new IngredientHolder(view);
        }

        @Override
        public void onBindViewHolder(IngredientHolder holder, int position) {
            Ingredient ingredient = mIngredients.get(position);
            holder.bindIngredient(ingredient);
        }

        @Override
        public int getItemCount() {
            return mIngredients.size();
        }

        public void setIngredients(List<Ingredient> ingredients) {
            mIngredients = ingredients;
        }

        void clearIngredients() {
            mIngredients.clear();
        }
    }
}
