package com.pirozhki.alcohall.ingredients.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.viewmodel.IngredientViewModel;
import com.pirozhki.alcohall.recipes.ui.RecipesActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IngredientsActivity extends AppCompatActivity implements AddIngredientDialogFragment.Listener {
    private IngredientAdapter mAdapter;
    private IngredientViewModel mIngredientViewModel;
    private TextView mAddFirstIngredientTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        mIngredientViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(IngredientViewModel.class);

        mAddFirstIngredientTextView = findViewById(R.id.add_first_ingredient_text_view);
        mAddFirstIngredientTextView.setOnClickListener(v -> showBottomSheetDialog());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        RecyclerView ingredientRecyclerView = findViewById(R.id.ingredient_recycler_view);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(mAdapter);
        updateIngredientsList();

        final Button findButton = findViewById(R.id.find_recipes_button);
        findButton.setOnClickListener(v -> {
            if (mAdapter.getItemCount() == 0) {
                new NoResultDialogFragment().show(getSupportFragmentManager(), NoResultDialogFragment.class.getName());
            } else {
                Intent intent = new Intent(this, RecipesActivity.class);
                intent.putExtra("ids", mIngredientViewModel.getIngredientsIds());
                startActivity(intent);
            }
        });

        final Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(v -> {
            mIngredientViewModel.clearIngredients();
            updateIngredientsList();
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_ingredients, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_new_ingredient) {
            showBottomSheetDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onIngredientSelected(Ingredient ingredient) {
        mIngredientViewModel.addIngredient(ingredient);
        updateIngredientsList();
    }

    private void updateIngredientsList() {
        if (mIngredientViewModel.areIngredientsEmpty())
            mAddFirstIngredientTextView.setVisibility(View.VISIBLE);
        else
            mAddFirstIngredientTextView.setVisibility(View.INVISIBLE);
        mAdapter.setIngredients(mIngredientViewModel.getIngredients());
        mAdapter.notifyDataSetChanged();
    }

    private void showBottomSheetDialog() {
        new AddIngredientDialogFragment().show(getSupportFragmentManager(), AddIngredientDialogFragment.class.getName());
    }

    private class IngredientHolder extends RecyclerView.ViewHolder {
        private Ingredient mIngredient;

        private TextView mTitleTextView;

        IngredientHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.ingredient_title_text_view);
            final ImageButton deleteButton = itemView.findViewById(R.id.delete_ingredient_button);
            deleteButton.setOnClickListener(v -> {
                mIngredientViewModel.removeIngredient(mIngredient);
                updateIngredientsList();
            });
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
            LayoutInflater layoutInflater = LayoutInflater.from(IngredientsActivity.this);
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
