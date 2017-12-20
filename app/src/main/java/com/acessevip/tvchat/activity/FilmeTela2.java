package com.acessevip.tvchat.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.acessevip.tvchat.R;
import com.acessevip.tvchat.activity.prefs.Prefs;
import com.acessevip.tvchat.util.MyWebView;

import static com.acessevip.tvchat.util.MyWebView.setWebClient;


public class FilmeTela2 extends AppCompatActivity implements Runnable{

    private Handler handler;
    private int mudalogo = 0;
    private int progresso = 0;
    String URL = "http://barretos.tv/xat/sala-1.php";
    private ImageView img_play;
    private TextView contagem;
    private WebView webview;
    private EditText nChat;
    private LinearLayout help;
    private FrameLayout web;
    private ProgressBar progress;
    //private String urlProg ="http://livestreamtv.biz/apptvcanais/canaltv/redirect/php_facebook.php";
    private int bloqueio = 0;
    private int mt = 0;
    private CountDownTimer mCountDownTimer;
    private long mTimeRemaining;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_tela2);


        nChat = (EditText) findViewById(R.id.edTextNomeChat);
        nChat.setFocusableInTouchMode(false);
        nChat.setFocusable(false);
        img_play = (ImageView) findViewById(R.id.img_play_tela2);
        help = (LinearLayout) findViewById(R.id.LayoutCadChat);
        web = (FrameLayout) findViewById(R.id.FrameLayoutWeb);
        contagem = (TextView) findViewById(R.id.txt_contagem);
        webview = (WebView) findViewById(R.id.webView_tela2);
        progress = (ProgressBar)findViewById(R.id.pb_webView_tela2);
        mt=1;
        //setWebClient(webview);
        if(Prefs.getString(this,this.getString(R.string.NCHAT)).equals("")|| Prefs.getString(this,this.getString(R.string.NCHAT)).equals(null)){
            //StChat.setText("Digite um nome :");
        }else {
            //StChat.setText("Seu nome no VipChat : ");
            nChat.setText(Prefs.getString(this,this.getString(R.string.NCHAT)));
        }
        handler =  new Handler();
        createTimer(12);

        //handler.post(this);



    }

    @Override
    protected void onDestroy() {
        mCountDownTimer.cancel();
        super.onDestroy();
    }

    public void play_prog_Tv (View v) {


            Intent it = new Intent(this,MainActivity.class);
            startActivity(it);
            finish();
        }

    public void chat_trava (View v){

        mCountDownTimer.cancel();
        String urlUser = nChat.getText().toString();

        if(!(urlUser.equals("")||urlUser.equals(null))) {
            Prefs.setString(this,this.getString(R.string.NCHAT),urlUser);
            String url1 = "http://www7.cbox.ws/box/?boxid=552072&boxtag=r8595d&sec=profile&n=";
            String url2 ="&k=";
            setWebClient(webview,url1+urlUser+url2,progress);
            handler.postDelayed(this,200);
            mt+=1;

        }
        else
        {
            Toast.makeText(this,"Digite um nome para usar no Chat !",Toast.LENGTH_LONG).show();
        }

    }


    public void chat1 (View v){

        setWebClient(webview,"http://barretos.tv/xat/sala-1.php",progress);
        avancar();
    }

    public void chat2 (View v){
        setWebClient(webview,"http://barretos.tv/xat/sala-2.php",progress);
        avancar();
    }

    public void chat3 (View v){
        setWebClient(webview,"http://barretos.tv/xat/sala-3.php",progress);
        avancar();
    }

    public void chat4 (View v){
        setWebClient(webview,"http://barretos.tv/xat/sala-4.php",progress);
        avancar();
    }

    public void chat5 (View v){
        setWebClient(webview,"http://barretos.tv/xat/sala-5.php",progress);
        avancar();
    }

    public void chat6 (View v){
        setWebClient(webview,"http://barretos.tv/xat/sala-6.php",progress);
        avancar();
    }

    public void chat7 (View v){
        setWebClient(webview,"http://barretos.tv/xat/sala-7.php",progress);
        avancar();
    }

    @Override
    public void run() {

        mudaTela(mt);
    }

    public void avancar() {

        handler.postDelayed(this, 200);
        mt+=1;
        mCountDownTimer.cancel();


    }

    public void mudaTela(int mt){
        if (mt == 2) {
            help.setVisibility(View.GONE);
            web.setVisibility(View.VISIBLE);



        }else{
            //setWebClient(webview,);
        }

    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }


    public void createTimer(long time) {

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(time * 1000, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                mTimeRemaining = ((millisUnitFinished / 1000) + 1);
                contagem.setText("Entrando em " + mTimeRemaining + " s");
            }

            @Override
            public void onFinish() {

                Intent it = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(it);
                finish();


            }
        };
        mCountDownTimer.start();
    }

    public void poeNome(View view) {
        nChat.setFocusableInTouchMode(true);
        nChat.setFocusable(true);
        mCountDownTimer.cancel();

    }
}
