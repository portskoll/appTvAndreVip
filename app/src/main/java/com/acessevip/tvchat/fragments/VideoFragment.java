package com.acessevip.tvchat.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.acessevip.tvchat.domain.Carro;
import com.acessevip.tvchat.R;

/**
 * Created by Henrique on 26/11/2015.
 */
public class VideoFragment extends BaseFragment{
    private ProgressBar  pb;
    VideoView videoView;


    int posicao = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            //setRetainInstance(true);
            posicao = savedInstanceState.getInt("pos");



        }
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_video,container,false);
        videoView = (VideoView)view.findViewById(R.id.videoView);
        pb = (ProgressBar) view.findViewById(R.id.progressVideo);

        Carro c = (Carro) getArguments().getSerializable("carro");

        if (c != null) {
            videoView.setVideoURI(Uri.parse(c.urlVideo));
            videoView.setMediaController(new MediaController(getContext()));
            videoView.buildDrawingCache(true);



            videoView.start();
            pb.setVisibility(View.VISIBLE);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.seekTo(posicao);
                    mp.start();

                    pb.setVisibility(View.GONE);

                }
            });



        }


        return view;
    }

    private int s (int seg) {

        int segundos = seg*1000;

        return segundos;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        if(videoView.isPlaying()) {
            videoView.pause();
            outState.putInt("pos", (videoView.getCurrentPosition() > s(3)) ? videoView.getCurrentPosition() - s(3) : videoView.getCurrentPosition());


        }
    }
}
