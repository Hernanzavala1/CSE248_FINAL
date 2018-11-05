package com.example.herna.cse248_final.common;

import com.example.herna.cse248_final.NewsService;
import com.example.herna.cse248_final.retrofit.RetrofitClient;
import com.example.herna.cse248_final.weatherModel.WeatherInfo;

public class Common {

    public String News_BASE_URL = "https://newsapi.org";
    //weather base url
    // https://samples.openweathermap.org/data/2.5/
    public  String apiKey ="0daed7fe19474840bf9112c58db85757";



    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient("https://newsapi.org").create(NewsService.class);
    }

    public static WeatherInfo weatherService(){
        return RetrofitClient.getClient("https://api.openweathermap.org/data/2.5/").create(WeatherInfo.class);
    }

    public static String getAPIUrl(String Source)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(Source).
                append("&apiKey=").append("0daed7fe19474840bf9112c58db85757").toString();
    }
}
