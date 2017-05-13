package com.dekoraktiv.android.rsr.endpoints;

import com.dekoraktiv.android.rsr.models.Stem;
import com.dekoraktiv.android.rsr.models.Suggestion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApiService {
    @GET("http://hjp.znanje.hr/hjp_ajax.php/")
    Call<List<Suggestion>> getSuggestions(@Query("term") String term);

    @POST("http://hjp.znanje.hr/index.php?show=search")
    @FormUrlEncoded
    Call<Stem> getStemByWord(@Field("word") String word);

    @GET("http://hjp.znanje.hr/index.php?show=search_by_id")
    Call<Stem> getStemById(@Query("id") String id);
}
