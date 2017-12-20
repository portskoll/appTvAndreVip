package com.acessevip.tvchat.activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.acessevip.tvchat.R;


import livroandroid.lib.utils.AndroidUtils;

public class Splash extends AppCompatActivity implements Runnable{

    private Handler h;
    private int progresso=6;
    TextView status;
    int mudalogo=0;
    String Rede = "";
    //private AdcashInterstitial mInterstitial;


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //mInterstitial.loadAd();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(AndroidUtils.isNetworkAvailable(this)){
            //propaganda adcash
           /*mInterstitial = new AdcashInterstitial("1130435", this);
           // mInterstitial = new AdcashInterstitial("409357", this); //esse é teste

            mInterstitial.setAdListener(new AdcashInterstitial.AdListener() {
                @Override
                public void onAdLoaded() {
                    Toast.makeText(Splash.this, "Ad loaded",
                            Toast.LENGTH_SHORT).show();
                    mInterstitial.showAd();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    final String message;
                    switch (errorCode) {
                        case AdcashAdRequestFailedError.NO_NETWORK:
                            message = "No internet connection";
                            break;
                        case AdcashAdRequestFailedError.REQUEST_FAILED:
                            message = "Request failed";
                            break;
                        case AdcashAdRequestFailedError.NETWORK_FAILURE:
                            message = "Network failure";
                            break;
                        case AdcashAdRequestFailedError.NO_AD:
                            message = "There is no ad";
                            break;
                        default:
                            message = "Some other problem";
                    }
                    Toast.makeText(Splash.this, message,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdOpened() {
                    Toast.makeText(Splash.this, "Ad opened",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdClosed() {
                    Toast.makeText(Splash.this, "Ad closed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLeftApplication() {
                    Toast.makeText(Splash.this,
                            "Ad left Application", Toast.LENGTH_SHORT).show();
                }
            });*/

            Rede = "OK";
        }
        h = new Handler();
        h.post(this);




    }
    @Override
    public void run() {
        ImageView logo = (ImageView)findViewById(R.id.Splash_img);
        avancar();

        if(mudalogo==1) {

           status = (TextView)findViewById(R.id.txtStatus);
            status.setText(Integer.toString(mudalogo).toString());
            logo.setImageResource(R.drawable.splash1);



        }

        if(mudalogo==2) {

            status = (TextView)findViewById(R.id.txtStatus);
            status.setText(Integer.toString(mudalogo).toString());
            logo.setImageResource(R.drawable.splash2);



        }

        if(mudalogo == 3) {
            mudalogo=0;

        }


        if (progresso > 6 && progresso < 20) {
            avancar();
           status = (TextView)findViewById(R.id.txtStatus);
            status.setText("Verificando Conexão...");


        }

        if (progresso >= 20 && progresso < 30) {

            if (Rede.equals("OK")) {
               status = (TextView)findViewById(R.id.txtStatus);
                status.setText("Rede OK");
                progresso = 30;
                    Intent it = new Intent(this,FilmeProgTv.class);
                    startActivity(it);
                    finish();
            }else {

                progresso = 19;

                status.setText("Sem conexão com a Internet.\nConecte-se a Internet e tente novamente.");
            }

        }

    }

    public void avancar() {

        h.postDelayed(this,600);
        mudalogo++;
        progresso++;

    }
}
