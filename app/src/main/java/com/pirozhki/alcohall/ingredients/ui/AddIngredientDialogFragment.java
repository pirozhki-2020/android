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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.viewmodel.IngredientViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddIngredientDialogFragment extends DialogFragment {
    private final IngredientAdapter mAdapter = new IngredientAdapter();

    private Listener mListener;
    private IngredientViewModel mIngredientViewModel;
    private TextView mNoResultsTextView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()));
        bottomSheetDialog.setContentView(R.layout.content_add_ingr_dialog);
        final View bottomSheetInternal = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(Objects.requireNonNull(bottomSheetInternal)).setPeekHeight(1600);
        bottomSheetDialog.show();

        mNoResultsTextView = bottomSheetInternal.findViewById(R.id.no_results_text_view);

        mIngredientViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(IngredientViewModel.class);
        RecyclerView ingredientRecyclerView = bottomSheetInternal.findViewById(R.id.add_ingredient_recycler_view);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity())));
        ingredientRecyclerView.setAdapter(mAdapter);

        final Button backFromAddButton = bottomSheetInternal.findViewById(R.id.back_from_add_button);
        backFromAddButton.setOnClickListener(v -> bottomSheetDialog.cancel());

        final TextInputLayout searchIngredientTextFiled = bottomSheetInternal.findViewById(R.id.searchIngredientTextField);
        searchIngredientTextFiled.setEndIconOnClickListener(v -> {
            mNoResultsTextView.setVisibility(View.INVISIBLE);
            final TextInputEditText findIngredient = bottomSheetDialog.findViewById(R.id.searchIngredientEditText);
            String query = Objects.requireNonNull(Objects.requireNonNull(findIngredient).getText()).toString();
            mIngredientViewModel.findIngredients(query);
        });

        // Handle changes emitted by LiveData
        mIngredientViewModel.getApiResponse().observe(Objects.requireNonNull(getActivity()), apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getIngredients());
            }
        });

        return bottomSheetDialog;
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

    private void handleError(Throwable error) {
        mAdapter.clearIngredients();
        mAdapter.notifyDataSetChanged();
        mNoResultsTextView.setVisibility(View.VISIBLE);
        Log.e(AddIngredientDialogFragment.class.getName(), "error occurred while get api response: " + error.toString());
    }

    private void handleResponse(List<Ingredient> ingredients) {
        if (ingredients != null && ingredients.size() > 0) {
            mAdapter.setIngredients(ingredients);
            mAdapter.notifyDataSetChanged();
            mNoResultsTextView.setVisibility(View.INVISIBLE);
        } else {
            mAdapter.clearIngredients();
            mAdapter.notifyDataSetChanged();
            mNoResultsTextView.setVisibility(View.VISIBLE);
        }
    }

    private class IngredientHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Ingredient mIngredient;

        private TextView mTitleTextView;

        IngredientHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.add_ingredient_title_text_view);
            itemView.setOnClickListener(this);
        }

        void bindIngredient(Ingredient ingredient) {
            mIngredient = ingredient;
            mTitleTextView.setText(mIngredient.getName());
        }

        @Override
        public void onClick(View v) {
            mListener.onIngredientSelected(mIngredient);
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
