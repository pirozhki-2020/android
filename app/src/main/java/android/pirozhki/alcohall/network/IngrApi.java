package android.pirozhki.alcohall.network;

import android.pirozhki.alcohall.ingredients.Ingredient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IngrApi {

    class IngredientsPlain {
        public String status;
        public List<Ingredient> ingredients;
    }

    @GET("/cocktails.list_ingredients?query={query}")
    Call<List<IngredientsPlain>> getAll(@Path("query") String query);
}
