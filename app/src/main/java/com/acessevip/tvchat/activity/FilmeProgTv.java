package com.acessevip.tvchat.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.acessevip.tvchat.util.PrefUser;
import com.acessevip.tvchat.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class FilmeProgTv extends AppCompatActivity implements Runnable{

    private Handler handler;
    private int mudalogo = 0;
    private int progresso = 0;
    private ImageView img_play;
    private WebView webProgramacao;
    private ProgressBar progress;
    private String urlProg ="http://livestreamtv.biz/apptvcanais/canaltv/redirect/php_prog_filmes_tv.php";
    private static final String urlRedirect= "http://livestreamtv.biz/appaviso/aviso.php";
    InterstitialAd mInterstitialAd_1 ;
    private int prop_interstitial = 0;
    private int close_interstitial = 0;
    private int bloqueio = 0;


    @Override
    protected void onDestroy() {



        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_prog_tv);
        img_play = (ImageView) findViewById(R.id.img_play_prog_filmes);
        progress = (ProgressBar)findViewById(R.id.pb_webView_progFilmes);
        handler =  new Handler();
        handler.post(this);


        //adMob - interstitial
        mInterstitialAd_1 = new InterstitialAd(this);
        mInterstitialAd_1.setAdUnitId("ca-app-pub-4051267742072386/3993372755");

        AdRequest adRequest_1 = new AdRequest.Builder()
            //.addTestDevice("23DC7227932B734FD41A35786991BF66")
                .build();

        mInterstitialAd_1.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                if(PrefUser.getUserID(getBaseContext(), "id") == -1) {
                    Intent it = new Intent(getBaseContext(),Cadastro.class);
                    startActivity(it);
                    finish();
                }else {
                    Intent it = new Intent(getBaseContext(),FilmeTela2.class);
                    startActivity(it);
                    finish();
                }
                super.onAdClosed();
            }

        });

        mInterstitialAd_1.loadAd(adRequest_1);

    }

    public void play_prog_Tv (View v) {



        avancar();

    }

    @Override
    public void run() {

        if (close_interstitial == 0) {
            if  ( mInterstitialAd_1.isLoaded())  {
                mInterstitialAd_1.show();
                close_interstitial = 1;
            }else {
                if (bloqueio < 3) {
                    bloqueio++;
                }else{
                    prop_interstitial = 1;
                    bloqueio=0;
                }

            }
        }


        if(prop_interstitial == 1) {
            if(PrefUser.getUserID(this, "id") == -1) {
                Intent it = new Intent(this,Cadastro.class);
                startActivity(it);
                finish();
            }else {
                Intent it = new Intent(this,FilmeTela2.class);
                startActivity(it);
                finish();
            }

            prop_interstitial = 0;
        }

        if(progresso == 0) {
            programacao();
            progresso =1;
        }


        if (mudalogo == 0) {
            img_play.setImageResource(R.drawable.play_1);
            mudalogo = 1;

        }else {
            img_play.setImageResource(R.drawable.play_2);
            mudalogo = 0;
        }

    }

    public void avancar() {

        handler.postDelayed(this,500);


    }

    public void programacao(){

        webProgramacao = (WebView) findViewById(R.id.webView_progFilmes);
        WebSettings webSettings = webProgramacao.getSettings();
        //webSettings.setSupportZoom(true);
        //webSettings.setJavaScriptEnabled(true);
        webProgramacao.loadUrl(urlProg, null);
        //webProgramacao.setVisibility(View.VISIBLE);
        //webProgramacao.bringToFront();
        //webProgramacao.getApplicationWindowToken();

        webProgramacao.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlProg) {
                view.loadUrl(urlProg);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                progress.setVisibility(View.VISIBLE);
            }



            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                progress.setVisibility(View.INVISIBLE);

            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webProgramacao.canGoBack()) {
            webProgramacao.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }


}
