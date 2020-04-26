package android.pirozhki.alcohall.network;

import android.pirozhki.alcohall.model.Ingredient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IngrApi {

    class Ingredients {
        public String status;
        public List<Ingredient> ingredients;
    }

    @GET("/cocktails.list_ingredients?query={query}")
    Call<List<Ingredients>> getAll(@Path("query") String query);
}
