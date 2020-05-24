package com.pirozhki.alcohall.common;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    private static App sInstance;

    private AppDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = Room.databaseBuilder(this, AppDatabase.class, "alcohall-database").build();
    }

    public static App getInstance() {
        return sInstance;
    }

    public AppDatabase getDatabase() {
        return mDatabase;
    }
}
