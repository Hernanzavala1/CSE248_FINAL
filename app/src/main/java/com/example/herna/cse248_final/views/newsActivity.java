package com.example.herna.cse248_final.views;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.herna.cse248_final.NewsModel.Article;
import com.example.herna.cse248_final.NewsModel.News;
import com.example.herna.cse248_final.NewsService;
import com.example.herna.cse248_final.News_RecyclerView_Adapter.ItemClickListener;
import com.example.herna.cse248_final.News_RecyclerView_Adapter.RecyclerViewAdapter;
import com.example.herna.cse248_final.News_RecyclerView_Adapter.webviewClass;
import com.example.herna.cse248_final.R;
import com.example.herna.cse248_final.common.Common;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newsActivity extends AppCompatActivity implements ItemClickListener, AdapterView.OnItemSelectedListener{

    private NewsService newsService;

    public RecyclerView recyclerView;
    public TextView top_article_title;
    private SwipeRefreshLayout swipeRefreshLayout;
    public DiagonalLayout diagonalLayout;
    public KenBurnsView kbv;

    // news source
    private String Source ="abc-news";
    private Spinner spinner;

    private List<Article> articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // getting the news service
        newsService = Common.getNewsService();
        recyclerView = findViewById(R.id.recyclerView);
        kbv = findViewById(R.id.kbv);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        spinner  =(Spinner)findViewById(R.id.drop_Down_News_Source);
        top_article_title = findViewById(R.id.top_article_title);
        loadSpinner();
        spinner.setOnItemSelectedListener(this);

        // originally load from abc news and if user wants to change they can select from the list and reload the news
        // initialize the recycler
        loadNews(this.Source);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @TargetApi(Build.VERSION_CODES.P)
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onRefresh() {
                loadNews(Source);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);

            }
        });


    }

    private void loadSpinner() {

        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(this,R.array.News_Sources, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

    }


    public void loadNews(String Source){
        newsService.getArticles(Common.getAPIUrl(Source)).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Picasso.get()
                        .load(response.body().getArticles().get(0).getUrlToImage())
                        .into(kbv);
                // set title to the first article
                top_article_title.setText(response.body().getArticles().get(0).getTitle());

                articles = response.body().getArticles();

                kbv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setTopArticleUrl(articles);
                    }
                });

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

    private void setTopArticleUrl(List<Article> articles) {
        if(articles != null){
            Intent intent = new Intent(newsActivity.this, webviewClass.class);
            intent.putExtra("article_url", articles.get(0).getUrl());
            startActivity(intent);
        }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0 :
                Source = "cbs-news";
                break;
            case 1 :
                Source = "espn";
                break;
            case 2:
                Source ="cnn";
                break;
            case 3:
                Source = "mtv-news";
                break;
            case 4:
                Source = "nbc-news";
                break;
            default:
                Source ="abc-news";
        }
        loadNews(Source);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}