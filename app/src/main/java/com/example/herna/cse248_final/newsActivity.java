package com.example.herna.cse248_final;

import android.app.Activity;
import android.content.Intent;
import android.support.design.internal.NavigationMenuView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.herna.cse248_final.NewsModel.Article;
import com.example.herna.cse248_final.NewsModel.News;
import com.example.herna.cse248_final.News_RecyclerView_Adapter.ItemClickListener;
import com.example.herna.cse248_final.News_RecyclerView_Adapter.RecyclerViewAdapter;
import com.example.herna.cse248_final.News_RecyclerView_Adapter.webviewClass;
import com.example.herna.cse248_final.common.Common;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newsActivity extends AppCompatActivity implements ItemClickListener {

    private NewsService newsService;

    public RecyclerView recyclerView;
    private SwipeRefreshLayout layout;
    public DiagonalLayout diagonalLayout;
    public KenBurnsView kbv;


    private List<Article> articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // getting the news service
        newsService = Common.getNewsService();
    recyclerView = findViewById(R.id.recyclerView);
    kbv = findViewById(R.id.kbv);
    layout = findViewById(R.id.swipeRefresh);
    layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadNews();
           
        }
    });


        // initialize the recycler
        loadNews();



    }


    public void loadNews(){
        newsService.getArticles(Common.getAPIUrl()).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Picasso.get()
                        .load(response.body().getArticles().get(0).getUrlToImage())
                        .into(kbv);

                articles = response.body().getArticles();

                articles.remove(0);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(newsActivity.this, articles, newsActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(newsActivity.this));
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(newsActivity.this,"Something went wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickListener(View view, int position) {
    if(articles != null && view != null){
        Intent intent = new Intent(newsActivity.this, webviewClass.class);
        intent.putExtra("article_url", articles.get(position).getUrl());
        startActivity(intent);
    }
    else{
        Toast.makeText(this, "something went wrong in clickListener  method!", Toast.LENGTH_SHORT);
    }
    }
}
