package android.pirozhki.alcohall;


import android.app.Application;
import android.content.Context;
import android.pirozhki.alcohall.network.ApiRepo;

public class ApplicationModified extends Application {

    private ApiRepo mApiRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiRepo = new ApiRepo();
    }

    public ApiRepo getApis() {
        return mApiRepo;
    }

    public static ApplicationModified from(Context context) {
        return (ApplicationModified) context.getApplicationContext();
    }
}