package android.pirozhki.alcohall.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.pirozhki.alcohall.R;
import android.pirozhki.alcohall.ingredients.Ingredient;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BottomSheetDialogFragment extends DialogFragment {
    private RecyclerView mIngredientRecyclerView;
    private IngredientAdapter mAdapter;
    private List<Ingredient> mIngredients;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()));
        bottomSheetDialog.setContentView(R.layout.content_add_ingr_dialog);
        final View bottomSheetInternal = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(bottomSheetInternal).setPeekHeight(400);
        bottomSheetDialog.show();

        mIngredientRecyclerView = bottomSheetInternal.findViewById(R.id.add_ingredient_recycler_view);
        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity())));
        mIngredients = new ArrayList<>();
        mAdapter = new IngredientAdapter(mIngredients);
        mIngredientRecyclerView.setAdapter(mAdapter);

        final Button backFromAddButton = bottomSheetInternal.findViewById(R.id.back_from_add_button);
        backFromAddButton.setOnClickListener(v -> bottomSheetDialog.dismiss());

        final FloatingActionButton searchButton = bottomSheetInternal.findViewById(R.id.search_ingredient_button);
        searchButton.setOnClickListener(v -> {
            // TODO: поиск ингредиентов
            mIngredients.add(new Ingredient("Ром", 100));
            mAdapter.setIngredients(mIngredients);
            mAdapter.notifyDataSetChanged();
        });

        return bottomSheetDialog;
    }

    private void updateIngredientsList() {
        mIngredients = new ArrayList<>();
        if (mAdapter == null) {
            mAdapter = new IngredientAdapter(mIngredients);
            mIngredientRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setIngredients(mIngredients);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class IngredientHolder extends RecyclerView.ViewHolder {
        private Ingredient mIngredient;

        private TextView mTitleTextView;

        public IngredientHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.ingredient_title_text_view);
        }

        public void bindIngredient(Ingredient ingredient) {
            mIngredient = ingredient;
            mTitleTextView.setText(mIngredient.getTitle());
        }
    }

    private class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder> {
        public IngredientAdapter(List<Ingredient> ingredients) {
            mIngredients = ingredients;
        }

        @Override
        @NonNull
        public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(BottomSheetDialogFragment.this.getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_ingredient, parent, false);
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
    }
}
