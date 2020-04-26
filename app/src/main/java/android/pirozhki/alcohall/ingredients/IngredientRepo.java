package android.pirozhki.alcohall.ingredients;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.pirozhki.alcohall.network.ApiRepo;
import android.pirozhki.alcohall.network.IngrApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientRepo {
    private final static MutableLiveData<List<IngredientsList>> mInrgedients = new MutableLiveData<>();
    private final Context mContext;
    private IngrApi mIngrApi;

    public IngredientRepo(Context context) {
        mContext = context;
        mIngrApi = ApiRepo.from(mContext).getIngrApi();
    }

    public LiveData<List<IngredientsList>> getIngredients() {
        return mInrgedients;
    }

    /*public void refresh() {
        mIngrApi.getAll().enqueue(new Callback<List<IngrApi.IngredientsPlain>>() {
            @Override
            public void onResponse(Call<List<IngrApi.IngredientsPlain>> call, Response<List<IngrApi.IngredientsPlain>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mInrgedients.postValue(transform(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<IngrApi.IngredientsPlain>> call, Throwable t) {
                Log.e("ingredRepo", "Failed to load", t);
            }
        });
    }

    private static List<IngredientsList> transform(List<IngrApi.IngredientsPlain> plains) {
        List<IngredientsList> result = new ArrayList<>();
        for (IngrApi.IngredientsPlain ingrPlain : plains) {
            try {
                IngredientsList game = map(gamePlain);
                result.add(game);
                Log.e("LessonRepo", "Loaded " + game.theme);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }*/
}
