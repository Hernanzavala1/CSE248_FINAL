package com.example.herna.cse248_final;

import com.example.herna.cse248_final.NewsModel.News;

import retrofit2.http.GET;
import retrofit2.http.Url;
import retrofit2.Call;
public interface NewsService {
    // api key 0daed7fe19474840bf9112c58db85757
    @GET
    Call<News> getArticles (@Url String url);


}
