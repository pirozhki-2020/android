package com.pirozhki.alcohall.ingredients.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.viewmodel.AddIngredientViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddIngredientDialogFragment extends DialogFragment {
    private IngredientAdapter mAdapter;

    private Listener mListener;
    private AddIngredientViewModel mAddIngredientViewModel;
    private TextView mNoResultsTextView;
    private BottomSheetDialog mBottomSheetDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mBottomSheetDialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()));
        mBottomSheetDialog.setContentView(R.layout.content_add_ingr_dialog);
        final View bottomSheetInternal = mBottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(Objects.requireNonNull(bottomSheetInternal)).setPeekHeight(1600);
        mBottomSheetDialog.show();

        mNoResultsTextView = bottomSheetInternal.findViewById(R.id.no_results_text_view);

        mAddIngredientViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(AddIngredientViewModel.class);
        RecyclerView ingredientRecyclerView = bottomSheetInternal.findViewById(R.id.add_ingredient_recycler_view);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity())));
        mAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(mAdapter);

        final Button backFromAddButton = bottomSheetInternal.findViewById(R.id.back_from_add_button);
        backFromAddButton.setOnClickListener(v -> mBottomSheetDialog.cancel());

        final TextInputLayout searchIngredientTextFiled = bottomSheetInternal.findViewById(R.id.searchIngredientTextField);
        searchIngredientTextFiled.setEndIconOnClickListener(v -> {
            final TextInputEditText findIngredient = mBottomSheetDialog.findViewById(R.id.searchIngredientEditText);
            String query = Objects.requireNonNull(Objects.requireNonNull(findIngredient).getText()).toString();
            if (query.isEmpty()) {
                showNoResult();
            } else {
                mNoResultsTextView.setVisibility(View.INVISIBLE);
                mAddIngredientViewModel.findIngredients(query);
            }
        });

        // Handle changes emitted by LiveData
        mAddIngredientViewModel.getApiResponse().observe(Objects.requireNonNull(getActivity()), apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getIngredients());
            }
        });

        return mBottomSheetDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            mListener = (AddIngredientDialogFragment.Listener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetDialogFragment.Listener");
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        mAdapter.clearIngredients();
        mAdapter.notifyDataSetChanged();
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
                mListener.onIngredientSelected(mIngredient);
                mBottomSheetDialog.cancel();
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

    public interface Listener {
        void onIngredientSelected(Ingredient ingredient);
    }
}
