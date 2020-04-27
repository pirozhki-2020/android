package android.pirozhki.alcohall.ingredients;

public class Ingredient {
    private String mTitle;
    private int mVolumeMl;

    public Ingredient(String title, int volumeMl) {
        mTitle = title;
        mVolumeMl = volumeMl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getVolumeMl() {
        return mVolumeMl;
    }

    public void setVolumeMl(int volumeMl) {
        mVolumeMl = volumeMl;
    }
}
