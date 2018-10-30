package com.example.herna.cse248_final.common;

import com.example.herna.cse248_final.NewsService;
import com.example.herna.cse248_final.retrofit.RetrofitClient;

public class Common {

    public String BASE_URL = "https://newsapi.org";
    public  String apiKey ="0daed7fe19474840bf9112c58db85757";



    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient("https://newsapi.org").create(NewsService.class);
    }


    public static String getAPIUrl()
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append("abc-news").
                append("&apiKey=").append("0daed7fe19474840bf9112c58db85757").toString();
    }
}
