package com.acessevip.tvchat.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.acessevip.tvchat.domain.Carro;
import com.acessevip.tvchat.R;

public class WebViewVideo extends AppCompatActivity {

    private String urlVideo = "http://www.freelivestream.tv/power.php?file=band&width=480&height=400";// teste
    private WebView webView;
    private ProgressBar progress;
    private Carro carro;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent i = getIntent();
        //urlVideo= i.getStringExtra("canal");
        carro = (Carro) getIntent().getSerializableExtra("carro");
        //pega a url do video
        urlVideo = carro.urlVideo;
        //Full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_web_view_video);
        webView = (WebView) findViewById(R.id.webview);
        progress = (ProgressBar) findViewById(R.id.pbWebView);
        setWebViewCliente(webView);
        WebSettings webSet = webView.getSettings();
        webSet.setJavaScriptEnabled(true);

        //webView.loadUrl(urlVideo);
        webView.loadData("<html><body bgcolor='#000000'>"+"<div align='center'><a href="+urlVideo+"><img src='http://www.userlogos.org/files/logos/1air2philou/playtv-logo-v2_0.png' alt='Assistir Canal' width='300' height='200'></a></div>"+"</body></html>","text/html","UTF-8");


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void videofull(View v) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }


    }

    private void setWebViewCliente(final WebView webView) {
        webView.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                progress.setVisibility(View.INVISIBLE);

            }
        });


    }

    @Override
    public void onBackPressed() {

        if(webView.canGoBack()){

            webView.goBack();

            return;
        }

        Log.d("Webview","voltar");
        super.onBackPressed();
    }

    public void escondeMenu(View view) {
        videofull(webView);

    }
}
