package com.dekoraktiv.android.rsr.endpoints;

import com.dekoraktiv.android.rsr.models.Stem;
import com.dekoraktiv.android.rsr.models.Suggestion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiService {
    @GET("http://hjp.znanje.hr/hjp_ajax.php/")
    Call<List<Suggestion>> getSuggestions(@Query("term") String term);

    @GET("https://cdnapi.link/api/44d32fb571ad39745c4fe3b68cf0ce6c/020b8668/index.php/")
    Call<Stem> getStemByWord(@Query("w") String word);

    @GET("https://cdnapi.link/api/44d32fb571ad39745c4fe3b68cf0ce6c/020b8668/index.php/")
    Call<Stem> getStemById(@Query("i") String id);
}
