package com.example.herna.cse248_final.YoutubeRecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.herna.cse248_final.R;
import com.example.herna.cse248_final.YoutubePackage.Item;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class YoutubeRecyclerAdapter extends RecyclerView.Adapter<YoutubeRecyclerAdapter.MyViewHolder> {

    private List<Item>  videos;
    Context mContext;


    public YoutubeRecyclerAdapter( Context mContext,List<Item> videos) {
        this.videos = videos;
        this.mContext = mContext;


    }

    @NonNull
    @Override
    public YoutubeRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
       MyViewHolder viewHolder = new MyViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.view.initialize("AIzaSyCgygJ-AKJA601qwrTZdpCE6h9FfxPfK9Q", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                System.out.println("we have loaded the video at position "+  i);
                youTubePlayer.loadVideo(videos.get(i).id);
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                System.out.println("could not initilize video at position "+ i);
                }
        });

        myViewHolder.vidTitle.setText(videos.get(i).snippet.title);
         }



    @Override
    public int getItemCount() {
        return videos.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder  {
        private YouTubePlayerView view;
        private TextView vidTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.youtubeView);
            vidTitle = itemView.findViewById(R.id.videoTitle);

        }



    }

}
