package com.example.herna.cse248_final.YoutubePackage;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {
  @GET("videos?")
  Call<YoutubeModel> getYoutubeService (@Query("part")String part,  @Query("chart") String chart,
                                        @Query("maxResults") String maxResults, @Query("regionCode") String region,
                                        @Query("key") String apiKey);
}
