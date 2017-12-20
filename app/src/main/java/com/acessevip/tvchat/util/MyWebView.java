package com.acessevip.tvchat.util;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Henrique on 16/07/2017.
 */

public abstract class MyWebView {



    public static void setWebClient (WebView webview, String url, final ProgressBar progress) {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().getAllowContentAccess();//api 11 minimo
        webview.getSettings().getJavaScriptCanOpenWindowsAutomatically();
        webview.getSettings().supportMultipleWindows();
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setSavePassword(true);
        webview.getSettings().setSaveFormData(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setDomStorageEnabled (true);
        webview.setWebViewClient(new WebViewClient(){
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
        webview.setWebChromeClient(new WebChromeClient());
        webview.setInitialScale(160);
        webview.loadUrl(url);



    }

}
