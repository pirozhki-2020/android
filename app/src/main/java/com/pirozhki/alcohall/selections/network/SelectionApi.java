package com.pirozhki.alcohall.selections.network;

import com.pirozhki.alcohall.selections.model.Selection;
import com.squareup.moshi.Json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SelectionApi {
    @GET("/selection.get")
    Call<OneSelection> getSelection(@Query("id") String id);

    @GET("/selection.list")
    Call<Selections> getSelectionList(@Query("from_id") String from_id, @Query("limit") String limit);

    class SelectionsData {
        @Json(name = "selections")
        public List<Selection> selections;
    }

    class Selections {
        @Json(name = "status")
        public String status;
        @Json(name = "data")
        public SelectionsData data;
    }

    class OneSelection {
        @Json(name = "status")
        public String status;
        @Json(name = "data")
        public Selection data;
    }
}
