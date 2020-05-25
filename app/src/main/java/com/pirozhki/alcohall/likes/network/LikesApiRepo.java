package com.pirozhki.alcohall.likes.network;

import androidx.lifecycle.LiveData;


import java.util.ArrayList;

public interface LikesApiRepo {
    LiveData<LikesApiResponse> findRecipes();
}
