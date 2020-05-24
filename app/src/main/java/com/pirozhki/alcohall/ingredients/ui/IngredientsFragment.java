package com.pirozhki.alcohall.ingredients.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.viewmodel.IngredientViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class IngredientsFragment extends Fragment {
    private IngredientAdapter mAdapter;
    private IngredientViewModel mIngredientViewModel;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        mIngredientViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(IngredientViewModel.class);

        RecyclerView ingredientRecyclerView = view.findViewById(R.id.ingredient_recycler_view);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(mAdapter);
        updateIngredientsList();

        FloatingActionButton addIngredientButton = view.findViewById(R.id.add_ingredient_button);
        addIngredientButton.setOnClickListener(v -> findNavController(this).navigate(R.id.addIngredientFragment));

        final Button findButton = view.findViewById(R.id.find_recipes_button);
        findButton.setOnClickListener(v -> {
            if (mAdapter.getItemCount() == 0) {
                new NoResultDialogFragment().show(
                        requireActivity().getSupportFragmentManager(), NoResultDialogFragment.class.getName());
            } else {
                mCompositeDisposable.add(mIngredientViewModel.getIngredientsIds()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ingredientsIds -> {
                            Bundle bundle = new Bundle();
                            bundle.putIntegerArrayList("ids", new ArrayList<>(ingredientsIds));
                            findNavController(this).navigate(R.id.recipesFragment, bundle);
                        }));
            }
        });

        /*final Button clearButton = view.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(v -> mCompositeDisposable.add(mIngredientViewModel
                .clearIngredients()
                .subscribe(() -> {
                })));*/

        Bundle args = getArguments();
        if (args != null) {
            Ingredient ingredient = new Ingredient(args.getInt("ingredient_id"),
                    args.getString("ingredient_name"));
            mCompositeDisposable.add(mIngredientViewModel.addIngredient(ingredient).subscribe(() -> {
            }));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    private void updateIngredientsList() {
        mCompositeDisposable.add(mIngredientViewModel.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredients -> {
                    mAdapter.setIngredients(ingredients);
                    mAdapter.notifyDataSetChanged();
                }));
    }

    private class IngredientHolder extends RecyclerView.ViewHolder {
        private Ingredient mIngredient;

        private TextView mTitleTextView;

        IngredientHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.ingredient_title_text_view);
            final ImageButton deleteButton = itemView.findViewById(R.id.delete_ingredient_button);
            deleteButton.setOnClickListener(v -> mCompositeDisposable.add(mIngredientViewModel
                    .removeIngredient(mIngredient)
                    .subscribe(() -> {
                    })));
        }

        void bindIngredient(Ingredient ingredient) {
            mIngredient = ingredient;
            mTitleTextView.setText(mIngredient.getName());
        }
    }

    private class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder> {
        private List<Ingredient> mIngredients = new ArrayList<>();

        @Override
        @NonNull
        public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(IngredientsFragment.this.requireActivity());
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
