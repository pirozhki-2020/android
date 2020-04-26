package android.pirozhki.alcohall.ingredients;

public class Ingredient {
    public int id;
    public String name;

    private String mTitle;
    private int mVolumeMl;

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
