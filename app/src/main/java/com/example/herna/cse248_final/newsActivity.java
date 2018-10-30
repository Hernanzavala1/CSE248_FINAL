package com.example.herna.cse248_final;

import android.app.Activity;
import android.support.design.internal.NavigationMenuView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.herna.cse248_final.NewsModel.Article;
import com.example.herna.cse248_final.NewsModel.News;
import com.example.herna.cse248_final.News_RecyclerView_Adapter.RecyclerViewAdapter;
import com.example.herna.cse248_final.common.Common;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newsActivity extends AppCompatActivity {

    private NewsService newsService;

    public RecyclerView recyclerView;
    public DiagonalLayout diagonalLayout;
    public KenBurnsView kbv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsService = Common.getNewsService();
    recyclerView = findViewById(R.id.recyclerView);
    kbv = findViewById(R.id.kbv);

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

                List<Article> articles = response.body().getArticles();

                articles.remove(0);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(newsActivity.this, articles);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(newsActivity.this));
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(newsActivity.this,"Something went wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
