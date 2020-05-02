package com.pirozhki.alcohall.ingredients.ui;

import android.os.Bundle;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.model.Ingredient;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngredientsActivity extends AppCompatActivity implements AddIngredientDialogFragment.Listener {
    private RecyclerView mIngredientRecyclerView;
    private IngredientAdapter mAdapter;
    private List<Ingredient> mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        mIngredientRecyclerView = findViewById(R.id.ingredient_recycler_view);
        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateIngredientsList();

        final Button findButton = findViewById(R.id.find_recipes_button);
        findButton.setOnClickListener(v -> {
            // TODO: поиск рецептов
            new NoResultDialogFragment().show(getSupportFragmentManager(), NoResultDialogFragment.class.getName());
        });
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
        mIngredients.add(ingredient);
        updateIngredientsList();
    }

    public void onTextViewClick(View view) {
        showBottomSheetDialog();
    }

    private void updateIngredientsList() {
        // TODO: извлекать список ингредиентов из базы и обновлять mIngredients
        if (mAdapter == null) {
            mIngredients = new ArrayList<>();
            mAdapter = new IngredientAdapter(mIngredients);
            mIngredientRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setIngredients(mIngredients);
            mAdapter.notifyDataSetChanged();
        }
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
        }

        void bindIngredient(Ingredient ingredient) {
            mIngredient = ingredient;
            mTitleTextView.setText(mIngredient.getName());
        }
    }

    private class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder> {
        IngredientAdapter(List<Ingredient> ingredients) {
            mIngredients = ingredients;
        }

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
