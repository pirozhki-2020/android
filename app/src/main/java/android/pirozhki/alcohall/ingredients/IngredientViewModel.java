package android.pirozhki.alcohall.ingredients;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class IngredientViewModel extends AndroidViewModel {
    private IngredientRepo mRepo = new IngredientRepo(getApplication());
   // private LiveData<List<Lesson>> mLessons = mRepo.getLessons();

    public IngredientViewModel(@NonNull Application application) {
        super(application);
    }
    /*

    public void refresh() {
        mRepo.refresh();
    }*/
}
