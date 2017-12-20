package com.acessevip.tvchat.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.acessevip.tvchat.domain.Carro;
import com.acessevip.tvchat.activity.WebViewVideo;
import com.acessevip.tvchat.util.AvaliarAPP;
import com.squareup.picasso.Picasso;
import com.acessevip.tvchat.R;

import livroandroid.lib.utils.IntentUtils;

import static com.acessevip.tvchat.R.id.webview;
import static com.acessevip.tvchat.util.MyWebView.setWebClient;

/**
 * Created by Henrique on 18/11/2015.
 */
public class CarroFragment extends BaseFragment{

    private String tipo_play = "";
    private String URL_video = "";
    VideoView videoView;
    ProgressBar pb;
    ProgressBar progress;
    WebView webProgramacao;
    WebView webCanal;
    private FrameLayout l;
    private String urlProg ="http://livestreamtv.biz/apptvcanais/canaltv/redirect/php_facebook.php";//"http://livestreamtv.biz/appaviso/aviso.php";
    private Carro carro;

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carro,container,false);
        videoView = (VideoView)view.findViewById(R.id.vView_tvPQ);
        webProgramacao = (WebView) view.findViewById(R.id.webViewtvp);
        webCanal = (WebView) view.findViewById(R.id.WebViewCanal);
        progress = (ProgressBar)view.findViewById(R.id.pb_webView_tvp);
        pb = (ProgressBar)view.findViewById(R.id.pb_tvpq);
        l = (FrameLayout) view.findViewById(R.id.FrameCima);
        l.setVisibility(View.GONE);






        setHasOptionsMenu(true);//Informando o android que o fragmente contem menu


        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_play, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_video) {
            AvaliarAPP.agora(getActivity());
            if (tipo_play.equals("0")) {//player local
                Intent intent = new Intent(getActivity(),WebViewVideo.class);
                intent.putExtra("carro",carro);
                startActivity(intent);



                /*Intent intent = new Intent(getContext(),VideoActivity.class);
                intent.putExtra("carro",carro);
                startActivity(intent);*/
                return true;
            }else if (tipo_play.equals("1")){//player nativo
                IntentUtils.showVideo(getContext(),URL_video);
                return true;
            }else {// player para vk, youtube e html ou php
                IntentUtils.openBrowser(getContext(), URL_video);
                return true;
            }

        }
        return super.onOptionsItemSelected(item);

    }

    /*public void setCarro(String img) {

                    TextView textView = (TextView) getView().findViewById(R.id.tDesc);
                    textView.setText("oreia seca");
                    //setTextString(R.id.tDesc,"oreia seca");
                    ImageView imageView = (ImageView) getView().findViewById(R.id.fragment_carro_img);
                    Picasso.with(getContext()).load(img).fit().into(imageView);

            }*/
   public void setCarro(Carro carro) {
        if(carro != null){
            this.carro = carro;
            tipo_play = carro.tipo_play.toString();
            //tipo_play = "3";
            URL_video = carro.urlVideo.toString();
            Log.d("CarroFragment", tipo_play);
            final ImageView img_t_play = (ImageView) getView().findViewById(R.id.img_tipo_play);
            Log.d("url video", URL_video);
            setWebClient(webProgramacao,urlProg,progress);
            //programacao();

            if(tipo_play.equals("1")) {
                //webCanal.setVisibility(View.GONE);
                l.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoURI(Uri.parse(URL_video));
                videoView.setMediaController(new MediaController(getContext()));
                videoView.buildDrawingCache(true);
                videoView.start();
                pb.setVisibility(View.VISIBLE);

                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //mp.seekTo(0);


                        mp.start();
                        pb.setVisibility(View.GONE);

                    }
                });

            }else {
               /* canalNaWebView();
                videoView.setVisibility(View.GONE);*/
            }




           //altera a imagem do tipo de play
            if (tipo_play.equals("0")) {//player local

                img_t_play.setImageResource(R.drawable.android_play);

            }else if (tipo_play.equals("1")){//player nativo

                img_t_play.setImageResource(R.drawable.player_nativo);

            }else if (tipo_play.equals("2")){//player vk

                img_t_play.setImageResource(R.drawable.vk);

            }else if (tipo_play.equals("3")){//player youtube

                img_t_play.setImageResource(R.drawable.youtube);

            }else {//player no browser

                img_t_play.setImageResource(R.drawable.html);
            }
            setTextString(R.id.tDesc, carro.desc);
            final ImageView imageView = (ImageView) getView().findViewById(R.id.img_canal);
            Picasso.with(getContext()).load(carro.urlFoto).fit().into(imageView);
        }else toast("carro zerado");
    }

//    public void programacao(){
//
//
//        WebSettings webSettings = webProgramacao.getSettings();
//        webSettings.setSupportZoom(true);
//        webSettings.setJavaScriptEnabled(true);
//        webProgramacao.loadUrl(urlProg, null);
//        webProgramacao.setVisibility(View.VISIBLE);
//        webProgramacao.bringToFront();
//        webProgramacao.getApplicationWindowToken();
//
//        webProgramacao.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String urlProg) {
//                view.loadUrl(urlProg);
//                return false;
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                // TODO Auto-generated method stub
//                super.onPageStarted(view, url, favicon);
//                progress.setVisibility(View.VISIBLE);
//            }
//
//
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                // TODO Auto-generated method stub
//                super.onPageFinished(view, url);
//                progress.setVisibility(View.INVISIBLE);
//
//            }
//        });
//
//
//
//    }

//    public void canalNaWebView(){
//
//
//        WebSettings webSettings1 = webCanal.getSettings();
//        webSettings1.setSupportZoom(true);
//        webSettings1.setJavaScriptEnabled(true);
//        //webCanal.loadUrl(URL_video, null);
//        webCanal.setVisibility(View.VISIBLE);
//        webCanal.bringToFront();
//        webCanal.getApplicationWindowToken();
//        setWebViewCliente(webCanal);
//        webCanal.loadData("<html><body>"+"<div align='center'><a href="+URL_video+">Clck aqui para assistir o canal</a></div>"+"</body></html>","text/html","UTF-8");
//    }

//    private void setWebViewCliente(final WebView webView) {
//        webView.setWebViewClient(new WebViewClient(){
//
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                pb.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//
//                pb.setVisibility(View.INVISIBLE);
//
//            }
//        });
//
//
//    }


}
