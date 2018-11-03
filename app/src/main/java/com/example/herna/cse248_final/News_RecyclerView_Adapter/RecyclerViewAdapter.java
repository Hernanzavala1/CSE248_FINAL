package com.example.herna.cse248_final.News_RecyclerView_Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.herna.cse248_final.NewsModel.Article;
import com.example.herna.cse248_final.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    List<Article> articles;
    Context mcontext;
    private ItemClickListener itemClickListener;
    public RecyclerViewAdapter(Context context, List<Article> articles,ItemClickListener itemClickListener) {
        this.articles = articles;
        this.mcontext = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_news_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder parent, int position) {
        Picasso.get()
                .load(articles.get(position).getUrlToImage())
                .into(parent.Image);


        if(articles.get(position).getTitle().length() > 65)
            parent.article_Title.setText(articles.get(position).getTitle().substring(0,65)+"...");
        else
            parent.article_Title.setText(articles.get(position).getTitle());



    }

    @Override
    public int getItemCount() {
        return articles.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView Image;
        private TextView article_Title;
        private CardView parent_Layout;





        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Article_image) ;
            article_Title= itemView.findViewById(R.id.article_title);
            parent_Layout = itemView.findViewById(R.id.parent_layout);

            itemView.setOnClickListener(this);


        }
        @Override
        public void onClick(View v) {
        itemClickListener.onClickListener(v, getAdapterPosition());
        }

    }



}
