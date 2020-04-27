package android.pirozhki.alcohall.ingredients;

import java.util.List;

public class IngredientsList {
    private String mStatus;
    private List<Ingredient> mIngredients;

    IngredientsList(String _status, List<Ingredient> _list){
        mStatus = _status;
        mIngredients = _list;
    }

    public String getStatus(){
        return mStatus;
    }

    public List<Ingredient> getIngredients(){
        return mIngredients;
    }
}
