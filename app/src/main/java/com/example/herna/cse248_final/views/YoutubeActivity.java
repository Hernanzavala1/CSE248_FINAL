package com.example.herna.cse248_final.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.herna.cse248_final.R;
import com.example.herna.cse248_final.YoutubePackage.Item;
import com.example.herna.cse248_final.YoutubePackage.YoutubeModel;
import com.example.herna.cse248_final.YoutubePackage.YoutubeService;
import com.example.herna.cse248_final.YoutubeRecyclerAdapter.VideoClickListener;
import com.example.herna.cse248_final.YoutubeRecyclerAdapter.YoutubeRecyclerAdapter;
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
import java.util.List;
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
    private RecyclerView recyclerView;

    private List<Item> videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        recyclerView = findViewById(R.id.youtubeRecyclerView);
        service = Common.getYoutubeVids();
        service.getYoutubeService("snippet","mostPopular","5","US","AIzaSyCgygJ-AKJA601qwrTZdpCE6h9FfxPfK9Q").
                enqueue(new Callback<YoutubeModel>() {
                    @Override
                    public void onResponse(Call<YoutubeModel> call, Response<YoutubeModel> response) {
                        System.out.println("we are getting a response!!!!");
                        Toast.makeText(YoutubeActivity.this,"getting youtube service!", Toast.LENGTH_SHORT).show();
                        YoutubeModel youtubeModel = response.body();
                        if(youtubeModel != null) {
                            videos = youtubeModel.items;

                            System.out.println(videos.size() + " is the amount of videos uo therre");
                            YoutubeRecyclerAdapter adapter = new YoutubeRecyclerAdapter(YoutubeActivity.this, videos);

                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(YoutubeActivity.this));
                        }
                    }

                    @Override
                    public void onFailure(Call<YoutubeModel> call, Throwable t) {
                        System.out.println("we are failing and not getting the response from youtube!!!" + t.getMessage());
                        Toast.makeText(YoutubeActivity.this,"not getting youtube service!", Toast.LENGTH_SHORT).show();
                    }
                });







    }

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

