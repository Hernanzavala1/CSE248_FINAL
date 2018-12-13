package com.example.herna.cse248_final.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.herna.cse248_final.R;
import com.example.herna.cse248_final.YoutubePackage.YoutubeModel;
import com.example.herna.cse248_final.YoutubePackage.YoutubeService;
import com.example.herna.cse248_final.common.Common;
import com.example.herna.cse248_final.retrofit.RetrofitClient;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeActivity extends YouTubeBaseActivity {

    private String inline = "";
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private YoutubeService service;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        service = Common.getYoutubeVids();
        service.getYoutubeService("snippet","mostPopular","5","US","AIzaSyC-lvL1gDtrcMd_FIAx8nyTDvGJFqZK1M4").
                enqueue(new Callback<YoutubeModel>() {
                    @Override
                    public void onResponse(Call<YoutubeModel> call, Response<YoutubeModel> response) {
                        System.out.println("we are getting a response!!!!");
                        Toast.makeText(YoutubeActivity.this,"getting youtube service!", Toast.LENGTH_SHORT).show();
                        YoutubeModel youtubeModel = response.body();
                        String id =youtubeModel.items.get(0).id;
                        System.out.println("one of the vids id is "+ id);
                    }

                    @Override
                    public void onFailure(Call<YoutubeModel> call, Throwable t) {
                        System.out.println("we are failing and not getting the response from youtube!!!");
                        Toast.makeText(YoutubeActivity.this,"not getting youtube service!", Toast.LENGTH_SHORT).show();
                    }
                });


      //  mQueue = Volley.newRequestQueue(this);
       // getYoutubeVideos();

        initializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };



    }

//    private void getYoutubeVideos() {
//        String url ="https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=5&regionCode=US&key=AIzaSyC-lvL1gDtrcMd_FIAx8nyTDvGJFqZK1M4";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Toast.makeText(YoutubeActivity.this,"we are in response method!", Toast.LENGTH_SHORT).show();
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                String error = error.toString();
//                Toast.makeText(YoutubeActivity.this,, Toast.LENGTH_SHORT).show();
//            }
//        });
//      mQueue.add(request);
//    }
}
